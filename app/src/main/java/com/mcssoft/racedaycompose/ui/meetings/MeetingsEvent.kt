package com.mcssoft.racedaycompose.ui.meetings

sealed class MeetingsEvent {

    // The Refresh icon on the TopAppBar was selected.
    data class Refresh(val date: String = "") : MeetingsEvent()
    object RefreshFromDb : MeetingsEvent()

}