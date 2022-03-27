package com.mcssoft.racedaycompose.data.repository.preferences

sealed class Preference {

    object FromDbPref: Preference()

    object OnlyAuNzPref: Preference()

    object MeetingId: Preference()
}