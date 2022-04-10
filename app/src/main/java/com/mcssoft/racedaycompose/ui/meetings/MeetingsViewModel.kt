package com.mcssoft.racedaycompose.ui.meetings

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.Preference
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.AppState
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    private val prefs: IPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.loading())   // private mutable.
    val state: StateFlow<MeetingsState> = _state.asStateFlow()       // public read only.

    private val _appState = MutableStateFlow(AppState.initialise())
    val appState: StateFlow<AppState> = _appState.asStateFlow()

    init {
        viewModelScope.launch {
            val date = DateUtils().getDateToday()
            val fromDbPref = prefs.getPreference(Preference.FromDbPref) as Boolean
            val onlyAuNzPref = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
            // Update AppState.
            _appState.value = _appState.value.copy(
                date = date,
                byDbPref = fromDbPref,
                byAuNzPref = onlyAuNzPref)
            if(fromDbPref) {
                getMeetingsFromLocal(onlyAuNzPref)
            } else {
                _appState.value = _appState.value.copy(
                    isRefreshing = true,
                    meetingsDownloaded = false,
                    runnersDownloaded = false)
                // Get the initial load from the Api (Meetings/Races).
                setupBaseFromApi(date)
                // TBA ?
                delay(250)
//                // Get the associated Runners from the Api.
//                setupRunnersFromApi()
            }
       }
    }

    /**
     * Called from the Meetings screen. Do a complete reload of the Api data.
     */
    fun onEvent(event: MeetingsEvent) {
        when(event) {
            is MeetingsEvent.Refresh -> {
                val date = DateUtils().getDateToday()
                setupBaseFromApi(date)     // get Meetings and associated Races.
//                setupRunnersFromApi(date)     // get the Runners.
            }
        }
    }

    /**
     * Use case: SetupBaseFromApi.
     * Get the raw data from the Api (Meetings and Races).
     */
    private fun setupBaseFromApi(date: String) {
        viewModelScope.launch {
            raceDayUseCases.setupBaseFromApi(date).collect { result ->
                when {
                    result.loading -> {
                        _state.value = _state.value.copy(
                            exception = null,
                            status = MeetingsState.Status.Loading,
                            data = null)
                        _appState.value = _appState.value.copy().apply {
                            isRefreshing = true
                            meetingsDownloaded = false
                            runnersDownloaded = false
                        }
//                        _state.value = MeetingsState.loading()
                    }
                    result.failed -> {
                        _state.value = _state.value.copy().apply {
                            exception = Exception("[SetupBaseFromApi] ${result.exception}")
                            status = MeetingsState.Status.Failure
                            data = null
                        }
                        _appState.value = _appState.value.copy().apply {
                            isRefreshing = false
                        }
//                        _state.value = MeetingsState.failure(
//                            exception = Exception("[SetupBaseFromApi] ${result.exception}")
//                        )
                    }
                    result.successful -> {
                        val onlyAuNz = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
                        _appState.value = _appState.value.copy(
                            byAuNzPref = onlyAuNz,
                            meetingsDownloaded = true)
                        Log.d("TAG","[SetupBaseFromApi] result.successful")
                        getMeetingsFromLocal(onlyAuNz)
                    }
                }
            }
        }
    }

    /**
     * Use case: GetMeetings.
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetingsFromLocal(onlyAuNz: Boolean) {
        viewModelScope.launch {
            raceDayUseCases.getMeetings(onlyAuNz).collect { result ->
                when {
                    result.loading -> {
                        _state.value = MeetingsState.loading()
                    }
                    result.failed -> {
                        _state.value = MeetingsState.failure(
                            exception = Exception(result.exception))
                    }
                    result.successful -> {
                        Log.d("TAG","getMeetingsFromLocal(onlyAuNz) result.successful")
                        _state.value = MeetingsState.success(data = result.data ?: emptyList())
                    }
                }
            }//.launchIn(viewModelScope)
        }
    }

    /**
     * Use case: SetupRunnersFromApi.
     * Get the raw data from the Api (Runners).
     * Note: This has to be done separately from the Meeting & Race info because of the Api. Runner
     *       info is per Meeting code, not in the generic list of Meetings and Races. So the Api has
     *       to be hit for each Meeting code.
     */
    fun setupRunnersFromApi(context: Context) {
        viewModelScope.launch {
            raceDayUseCases.setupRunnerFromApi(_appState.value.date, context).collect { result ->
                when {
                    result.failed -> {}
                    result.successful -> {}
                }
            }
        }
    }

}