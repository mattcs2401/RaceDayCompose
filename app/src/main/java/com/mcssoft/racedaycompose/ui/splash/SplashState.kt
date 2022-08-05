package com.mcssoft.racedaycompose.ui.splash

data class SplashState(
    var date: String = "",
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var loadingMsg: String,
    var baseFromApi: Boolean = false,
    var runnerFromApi: Boolean = false,
) {
    companion object {
        fun initialise(): SplashState {
            return SplashState(
                exception = null,
                status = Status.Initialise,
                loadingMsg = "Initialising."
            )
        }
    }

    sealed class Status {
        object Initialise : Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }
}
