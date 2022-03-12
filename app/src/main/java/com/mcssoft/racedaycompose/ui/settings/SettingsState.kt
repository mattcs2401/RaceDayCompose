package com.mcssoft.racedaycompose.ui.settings

data class SettingsState(
    var loading: Boolean = false,
    var error: String = "",
    var fromDbPref: Boolean = false
) {
    // TBA
}