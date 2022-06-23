package com.mcssoft.racedaycompose.ui.meetings

sealed class MeetingsEvent {

    // The Refresh icon on the TopAppBar was selected.
    data class Refresh(val date: String = "") : MeetingsEvent()

    // Get display items from the database.
    object RefreshFromDb : MeetingsEvent()

    object Cancel : MeetingsEvent() {

    }

//    // Check if Summary items exist.
//    object SummaryCheck : MeetingsEvent()

}