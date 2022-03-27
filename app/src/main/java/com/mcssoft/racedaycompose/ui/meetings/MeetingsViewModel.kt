package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.Preference
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.Constants.MEETING_TYPE
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    private val prefs: IPreferences
) : ViewModel() {
    /*
      ------
      Notes:
      ------
      Couldn't seem to get a Context object in here. Keep getting "leaks a Context object" warning.
     */

    private val _state = mutableStateOf(MeetingsState.initialise())
    val state: State<MeetingsState> = _state

    init {
        viewModelScope.launch {
            val date = DateUtils().getDateToday()
            val fromDbPref = prefs.getPreference(Preference.FromDbPref) as Boolean
            if(fromDbPref) {
                val onlyAuNzPref = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean
                getMeetings(onlyAuNzPref)
            } else {
                // Get the initial load from the Api (Meetings/Races).
                getFromApi(date)
                // Get the associated Runners from the Api.
                saveRunners(date)
            }
       }
    }

    /**
     * Called from the Meetings screen. Do a complete reload of the Api data.
     */
    fun onEvent(event: MeetingsEvent) {
        when(event) {
            is MeetingsEvent.Refresh -> {
                getFromApi(DateUtils().getDateToday())
            }
        }
    }

    /**
     * Use case: GetFromApi.
     * Get the raw data from the Api.
     */
    private fun getFromApi(date: String) {
        raceDayUseCases.getFromApi(date).onEach { result ->
            when {
                result.loading -> {
                    _state.value = MeetingsState.loading()
                }
                result.failed -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception("[GetFromApi] ${result.exception}"))
                }
                result.successful -> {
                    // Raw data has been fetched from the Api, so now save to database.
                    saveFromApi(result.data!!, MEETING_TYPE)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Use case: SaveFromApi.
     * Populate both Meeting and Race info from data retrieved from the Api.
     */
    private fun saveFromApi(raceDayDto: RaceDayDto, mtgType: String) {
        raceDayUseCases.saveFromApi(raceDayDto, mtgType).onEach { result ->
            when {
                result.loading -> {
                    _state.value = MeetingsState.loading()
                }
                result.failed -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception("[SaveFromApi] ${result.exception}"))
                }
                result.successful -> {
                    // Data saved to database, so now get list of Meetings.
                    val onlyAuNzPref = prefs.getPreference(Preference.OnlyAuNzPref) as Boolean

                    // Get the list of Meetings to display. Associated Races are already populated.
                    getMeetings(onlyAuNzPref)

//                    // Start the process to get all the Runner detail from the Api, and save to the
//                    // database.
//                    val date = DateUtils().getDateToday()
//                    execRunnersWorker(date)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Use case: GetMeetings.
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetings(onlyAuNz: Boolean) {
        raceDayUseCases.getMeetings(onlyAuNz).onEach { result ->
            when {
                result.loading -> {
                    _state.value = MeetingsState.loading()
                }
                result.failed -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception(result.exception))
                }
                result.successful -> {
                    _state.value = MeetingsState.success(data = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveRunners(date: String) {
        raceDayUseCases.saveRunners(date).onEach { result ->
            when {
                result.loading -> {}
                result.failed -> {}
                result.successful -> {}
            }
        }.launchIn(viewModelScope)
    }

}