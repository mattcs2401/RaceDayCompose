package com.mcssoft.racedaycompose.ui.settings

data class SettingsState(
    var loading: Boolean = false,      // general Preferences loading indicator.
    var error: String = "",            // text of any error message.

    var fromDbPref: Boolean = false,   // the use FromDb preference.

    var onlyAuPref: Boolean = false
    // TBA others.
)