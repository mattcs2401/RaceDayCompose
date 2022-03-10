package com.mcssoft.racedaycompose.ui.races

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.Constants
import com.mcssoft.racedaycompose.utility.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(RacesState())
    val state: State<RacesState> = _state

    init {
        Log.d("TAG","RacesViewModel.init()-savedStateHandle=${savedStateHandle.keys()}")

        savedStateHandle.get<Long>(Constants.PARAM_MEETING_ID)?.let { meetingId ->
            getMeeting(meetingId)
            getRaces(meetingId)
        }
    }

    private fun getRaces(mId: Long) {
        raceDayUseCases.getRaces(mId).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value.loading = true
                }
                is DataResult.Error -> {
                    _state.value.error = result.message
                    _state.value.loading = false
                }
                is DataResult.Success -> {
                    _state.value.races = result.data ?: emptyList()
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMeeting(mId: Long) {
        raceDayUseCases.getMeeting(mId).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value.loading = true
                }
                is DataResult.Error -> {
                    _state.value.error = result.message
                    _state.value.loading = false
                }
                is DataResult.Success -> {
                    _state.value.meeting = result.data
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

}
/*
data class RacesState(
    val meeting: Meeting? = null,           // the Meeting associated with the Races listing.
    val races: List<Race> = emptyList(),    // the list of Races.
    val isLoading: Boolean = false,         // a loading state flag.
    val error: String = "",                 // a string for error messages.
    var mId: Long = 0                       // the Meeting's id.
)
 */