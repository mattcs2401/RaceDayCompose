package com.mcssoft.racedaycompose.ui.races

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.meetings.MeetingsEvent
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState
import com.mcssoft.racedaycompose.utility.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
): ViewModel() {

    private val _state = mutableStateOf(RacesState())
    val state: State<RacesState> = _state

    fun onEvent(event: RacesEvent) {
        when(event) {
            is RacesEvent.GetRaces -> {
                getRaces(_state.value.mId)
            }
        }
    }

    private fun getRaces(mId: Long) {
        raceDayUseCases.getRaces(mId).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = RacesState(isLoading = true)
                }
                is DataResult.Error -> {
                    _state.value = RacesState(
                        error = result.message ?: "[getRaces()]: An unexpected error occurred."
                    )
                }
                is DataResult.Success -> {
                    _state.value = RacesState(races = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}