package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MeetingsState())
    val state: State<MeetingsState> = _state

    init {
        getMeetings()
    }

    fun onEvent(event: MeetingsEvent) {
        when(event) {
            is MeetingsEvent.Refresh -> {
                refreshMeetings()
            }
        }
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


    private fun refreshMeetings() {
        val date = DateUtils().getDateToday()
        raceDayUseCases.refreshMeetings(date).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = MeetingsState(isLoading = true)
                }
                is DataResult.Error -> {
                    _state.value = MeetingsState(
                        error = result.message ?: "[refreshMeetings()]: An unexpected error occurred."
                    )
                }
                is DataResult.Success -> {
                    getMeetings()
                }
            }
        }.launchIn(viewModelScope)
    }
}