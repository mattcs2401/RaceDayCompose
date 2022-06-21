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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
//    savedStateHandle: SavedStateHandle,
    private val prefs: IPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(MeetingsState.initialise())
    val state = _state.asStateFlow()

    private val _appState = MutableStateFlow(AppState.initialise())
    val appState = _appState.asStateFlow()

    init {
        viewModelScope.launch {
            val date = DateUtils().getDateToday()
            val fromDbPref = prefs.getPreference(Preference.FromDbPref) as Boolean
            val onlyAuNzPref = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
            // Update AppState.
            _appState.update { state ->
                state.copy(
                    date = date,
                    byDbPref = fromDbPref,
                    byAuNzPref = onlyAuNzPref
                )
            }
            if (fromDbPref) {
                getMeetingsFromLocal(onlyAuNzPref)
            } else {
                _appState.update { state ->
                    state.copy(
                        isRefreshing = true,
                        meetingsDownloaded = false,
                        runnersDownloaded = false
                    )
                }
                // Get the initial load from the Api (Meetings/Races).
                setupBaseFromApi(date)
            }
        }
    }

    /**
     * Called from the Meetings screen. Do a complete reload of the Api data.
     */
    fun onEvent(event: MeetingsEvent) {
        when (event) {
            is MeetingsEvent.Refresh -> {
                _appState.update { state ->
                    state.copy(
                        isRefreshing = true,
                        meetingsDownloaded = false,
                        runnersDownloaded = false
                    )
                }
                val date = DateUtils().getDateToday()
                setupBaseFromApi(date)     // get Meetings and associated Races.
            }
            is MeetingsEvent.RefreshFromDb -> {
                viewModelScope.launch {
                    val onlyAuNz = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
                    getMeetingsFromLocal(onlyAuNz)
                }
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
                        _state.update { state ->
                            state.copy(
                                loading = true,
                                status = MeetingsState.Status.Loading
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = Exception("[SetupBaseFromApi] ${result.exception}"),
                                status = MeetingsState.Status.Failure,
                                loading = false,
                            )
                        }
                        _appState.update { state ->
                            state.copy(isRefreshing = false)
                        }
                    }
                    result.successful -> {
                        val onlyAuNz = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
                        _appState.update { state ->
                            state.copy(
                                byAuNzPref = onlyAuNz,
                                meetingsDownloaded = true
                            )
                        }
                        Log.d("TAG", "[SetupBaseFromApi] result.successful")
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
                        _state.update { state ->
                            state.copy(
                                status = MeetingsState.Status.Loading,
                                loading = true
                            )
                        }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(
                                exception = Exception(result.exception),
                                status = MeetingsState.Status.Failure,
                                loading = false
                            )
                        }
                    }
                    result.successful -> {
                        Log.d("TAG", "getMeetingsFromLocal(onlyAuNz) result.successful")
                        _state.update { state ->
                            state.copy(
                                exception = null,
                                status = MeetingsState.Status.Success,
                                loading = false,
                                data = result.data ?: emptyList()
                            )
                        }
                    }
                }
            }//.launchIn(viewModelScope)
        }
    }

    /**
     * Check if any Summary items exist (basically just a count(*) operation).
     * @return true if records exist, else false.
     */
    fun summaryCheck(): Boolean {
        // TODO - Use case for Summary records check.
        return true
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
            delay(2000) // TBA ?
            raceDayUseCases.setupRunnerFromApi(_appState.value.date, context).collect { result ->
                when {
                    result.failed -> {
                        _appState.update { state ->
                            state.copy(
                                isRefreshing = false,
                                runnersDownloaded = false,
                                downloadError =
                                AppState.Status.DownloadError("Setup Runners from Api failed.")
                            )
                        }
                    }
                    result.successful -> {
                        _appState.update { state ->
                            state.copy(
                                isRefreshing = false,
                                runnersDownloaded = true
                            )
                        }
                    }
                }
            }
        }
    }

}