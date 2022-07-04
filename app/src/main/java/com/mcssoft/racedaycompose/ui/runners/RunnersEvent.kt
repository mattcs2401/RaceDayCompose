package com.mcssoft.racedaycompose.ui.runners

import android.content.Context

sealed class RunnersEvent {

    data class Retry(
        val rceId: Long = 0
    ): RunnersEvent()

    object Cancel: RunnersEvent()

    data class CheckRunner(
        val runnerId: Long,
        val checked: Boolean
    ): RunnersEvent()

    data class SetForSummary(
        val raceId: Long,
        val runnerId: Long,
        val checked: Boolean,
        val context: Context
    ): RunnersEvent()
}