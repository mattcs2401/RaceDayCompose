package com.mcssoft.racedaycompose.ui.splash

data class SplashState(
    var error: String = "",            // the operation resulted in an error.
    val finished: Boolean = true,      // the operation is in a finished state (successful).
    var initialising: Boolean = false  // the operation is initialising/starting.
)
