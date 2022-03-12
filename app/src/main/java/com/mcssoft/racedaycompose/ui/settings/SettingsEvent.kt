package com.mcssoft.racedaycompose.ui.settings

sealed class SettingsEvent {

    object GetFromDbPref: SettingsEvent()

    data class SaveFromDbPref(val fromDb: Boolean): SettingsEvent()
}