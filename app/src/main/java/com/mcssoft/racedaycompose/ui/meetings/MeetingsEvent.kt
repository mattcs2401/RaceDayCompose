package com.mcssoft.racedaycompose.ui.meetings

sealed class MeetingsEvent {

    // Loading means refresh state from the dB.
    data class Loading(val loading: Boolean = false): MeetingsEvent()

    // SelectMeeting means a Meeting item has been selected in the list. Go to assoc Races screen.
    data class SelectMeeting(val mtgId: Int = 0): MeetingsEvent()
}