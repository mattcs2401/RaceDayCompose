package com.mcssoft.racedaycompose.ui.races

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mcssoft.racedaycompose.ui.meetings.MeetingsEvent
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState

class RacesViewModel(): ViewModel() {

    private val _state = mutableStateOf(RacesState())
    val state: State<RacesState> = _state

    fun onEvent() { //event: MeetingsEvent) {
//        when(event) {
//            is MeetingsEvent.Refresh -> {
//                refreshMeetings()
//            }
//        }
    }

    init {
//        getRacess()
    }

}