package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.Constants.MEETING_TYPE
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    private val prefs: IPreferences,
    private val workerManager: WorkManager
) : ViewModel() {
    /*
      Notes: Couldn't seem to get a Context object in here. Keep getting a "leaks a Context object"
             warning.
     */

    private val _state = mutableStateOf(MeetingsState.initialise())
    val state: State<MeetingsState> = _state

    init {
        viewModelScope.launch {
            val fromDbPref = prefs.getPreference(PreferenceType.FromDbPref) as Boolean
            if(fromDbPref) {
                val onlyAuNzPref = prefs.getPreference(PreferenceType.OnlyAuNzPref) as Boolean
                getMeetings(onlyAuNzPref)
            } else {
                val date = DateUtils().getDateToday()
                getFromApi(date)
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
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState.loading()
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception("[GetFromApi] ${result.exception}"))
                }
                is DataResult.Success -> {
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
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState.loading()
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception("[SaveFromApi] ${result.exception}"))
                }
                is DataResult.Success -> {
                    // Data saved to database, so now get list of Meetings.
                    val onlyAuNzPref = prefs.getPreference(PreferenceType.OnlyAuNzPref) as Boolean

                    // Get the list of Meetings to display. Associated Races are already populated.
                    getMeetings(onlyAuNzPref)

                    // Start the process to get all the Runner detail from the Api, and save to the
                    // database.
                    val date = DateUtils().getDateToday()
                    //execRunnersWorker(date)
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
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState.loading()
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState.failure(
                        exception = Exception(result.exception))
                }
                is DataResult.Success -> {
                    _state.value = MeetingsState.success(data = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun execRunnersWorker(date: String) {
//        val codes =
//        _state.value.data.forEach { meeting ->
//            meeting.meetingCode
//        }
//        val data = workDataOf(
//            RaceDayApp.context.resources.getString(R.string.key_meeting_date) to date,
//            RaceDayApp.context.resources.getString(R.string.key_meeting_codes) to codes)
//        val worker = OneTimeWorkRequestBuilder<RunnersWorker>()
//            .setInputData(data)
//            .build()
//        workerManager.enqueue(worker)
    }
}