package com.mcssoft.racedaycompose.ui.meetings

import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dagger.hilt.android.lifecycle.HiltViewModel
import com.mcssoft.racedaycompose.utility.DateUtils
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.utility.Constants.MEETING_TYPE
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MeetingsState())
    val state: State<MeetingsState> = _state

    init {
        // Kick it all off.
        if(!_state.value.saved) {
            getFromApi(DateUtils().getDateToday())
        } else {
            getMeetings()
        }
    }

    fun onEvent(event: MeetingsEvent) {
        when(event) {
            is MeetingsEvent.Refresh -> {
                getFromApi(DateUtils().getDateToday())
            }
        }
    }

    private fun getFromApi(date: String) {
        raceDayUseCases.getFromApi(date).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState(isLoading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(
                        error = result.message ?: "[getFromApi()]: An unexpected error occurred."
                    )
                }
                is DataResult.Success -> {
                    _state.value = MeetingsState(raw = result.data!!)
                    // Raw data has been fetched from the Api, so now save to database.
                    saveFromApi(_state.value.raw!!, MEETING_TYPE)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFromApi(raceDayDto: RaceDayDto, mtgType: String) {
        raceDayUseCases.saveFromApi(raceDayDto, mtgType).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState(isLoading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(
                        error = result.message ?: "[getFromApi()]: An unexpected error occurred."
                    )
                }
                is DataResult.Success -> {
                    _state.value = MeetingsState(saved = true)
                    // Data saved to database, so now get list of Meetings.
                    getMeetings()
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get a list of Meetings from the database.
     * @note Database is already populated.
     */
    private fun getMeetings() {
        raceDayUseCases.getMeetings().onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState(isLoading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(
                        error = result.message ?: "[getMeetings()]: An unexpected error occurred."
                    )
                }
                is DataResult.Success -> {
                    _state.value = MeetingsState(meetings = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}