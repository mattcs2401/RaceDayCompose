package com.mcssoft.racedaycompose.ui.meetings

sealed class MeetingsEvent {

    data class Initialising(val loading: Boolean = true): MeetingsEvent()

    data class Loading(val loading: Boolean = false): MeetingsEvent()

    data class SelectMeeting(val mtgId: Int = 0): MeetingsEvent()
}