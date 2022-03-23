package com.mcssoft.racedaycompose.data.repository.preferences

sealed class PreferenceType {

    object FromDbPref: PreferenceType()

    object OnlyAuNzPref: PreferenceType()

    object MeetingId: PreferenceType()
}