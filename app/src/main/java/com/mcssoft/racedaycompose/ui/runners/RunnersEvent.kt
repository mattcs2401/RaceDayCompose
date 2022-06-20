package com.mcssoft.racedaycompose.ui.runners

sealed class RunnersEvent {

    data class Retry(val rceId: Long = 0): RunnersEvent()

    object Cancel: RunnersEvent()

    data class CheckRunner(val runnerId: Long, val checked: Boolean): RunnersEvent()
}