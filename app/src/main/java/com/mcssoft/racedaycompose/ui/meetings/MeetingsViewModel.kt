package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.Constants.MEETING_TYPE
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    private val prefs: IPreferences
) : ViewModel() {

    private val _state = mutableStateOf(MeetingsState())
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
                    _state.value = MeetingsState(loading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(error = result.message)
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
     * Populate Meeting/Race info from data retrieved from the Api.
     */
    private fun saveFromApi(raceDayDto: RaceDayDto, mtgType: String) {
        raceDayUseCases.saveFromApi(raceDayDto, mtgType).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState(loading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(error = result.message)
                }
                is DataResult.Success -> {
                    // Data saved to database, so now get list of Meetings.
                    val onlyAuNzPref = prefs.getPreference(PreferenceType.OnlyAuNzPref) as Boolean
                    getMeetings(onlyAuNzPref)
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
                    _state.value = MeetingsState(loading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(error = result.message)
                }
                is DataResult.Success -> {
                    _state.value = MeetingsState(meetings = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }


}