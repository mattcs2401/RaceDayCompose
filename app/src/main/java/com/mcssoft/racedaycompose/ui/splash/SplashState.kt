package com.mcssoft.racedaycompose.ui.splash

data class SplashState(
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var prePopulated: Boolean
) {
    companion object {
        fun initialise(): SplashState {
            return SplashState(
                exception = null,
                status = Status.Initialise,
                prePopulated = false
            )
        }
    }

    sealed class Status {
        object Initialise: Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }
}
