package com.mcssoft.racedaycompose.ui.settings

data class FromDbState(
    var loading: Boolean = false,      // general Preferences loading indicator.
    var error: String = "",            // text of any error message.
    var preference: Boolean = false   // the use FromDb preference.
)

data class OnlyAuNzState(
    var loading: Boolean = false,      // general Preferences loading indicator.
    var error: String = "",            // text of any error message.
    var preference: Boolean = false   // the use FromDb preference.
)