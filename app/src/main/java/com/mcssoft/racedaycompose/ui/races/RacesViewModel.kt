package com.mcssoft.racedaycompose.ui.races

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
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
        /*
          Notes:
          The Races screen expects a "meetingId" (supplied in the navigation from the MeetingsScreen
          to the RunnersScreen). However, when navigating back from the Runners screen, we have to
          supply something ?? A navigation default value can't be used as the meetingId is only
          known at runtime.
         */
        savedStateHandle.get<Long>(Constants.KEY_MEETING_ID)?.let { meetingId ->
            if(meetingId > 0) {
                // Save the Meeting id to the preferences.
                saveMeetingId(PreferenceType.MeetingId, meetingId)
                // Get Meeting and Races values for the screen.
                getMeeting(meetingId)
                getRaces(meetingId)
            } else {
                // Get the Meeting id from the preferences.
                getMeetingId(PreferenceType.MeetingId)
                // Meeting id is returned in the state.
                val mId = _state.value.meetingId
                // Get Meeting and Races values for the screen.
                getMeeting(mId)
                getRaces(mId)
            }
        }
    }

    private fun getRaces(mId: Long) {
        raceDayUseCases.getRaces(mId).onEach { result ->
            when {
                result.loading -> {
                    _state.value.loading = true
                }
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                        "[getRaces] An unknown error or exception occurred."
                    _state.value.loading = false
                }
                result.successful -> {
                    _state.value.races = result.data ?: emptyList()
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getMeeting(mId: Long) {
        raceDayUseCases.getMeeting(mId).onEach { result ->
            when {
                result.loading -> {
                    _state.value.loading = true
                }
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                        "[getMeeting] An unknown error or exception occurred."
                    _state.value.loading = false
                }
                result.successful -> {
                    _state.value.meeting = result.data
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveMeetingId(prefType: PreferenceType.MeetingId, meetingId: Long) {
        raceDayUseCases.savePreferences(prefType, meetingId).onEach { result ->
            when {
                result.loading -> {}
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                            "[saveMeetingId] An unknown error or exception occurred."
                }
                result.successful -> {
                    _state.value.meetingId = meetingId
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getMeetingId(prefType: PreferenceType.MeetingId) {
        raceDayUseCases.getPreferences(prefType).onEach { result ->
            when {
                result.loading -> {}
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                            "[getMeetingId] An unknown error or exception occurred."
                }
                result.successful -> {
                    _state.value.meetingId = result.data as Long
                }
            }
        }.launchIn(viewModelScope)
    }
}
