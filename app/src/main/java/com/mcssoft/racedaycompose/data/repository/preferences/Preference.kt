package com.mcssoft.racedaycompose.data.repository.preferences

sealed class Preference {

    object FromDbPref : Preference()

    object OnlyAuNzPref : Preference()

    // App specific preference, i.e. not user selectable. MeetingId is saved on navigation from Race
    // to Runner (for when back nav to Race).
    object MeetingIdPref : Preference()
}