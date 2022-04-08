package com.mcssoft.racedaycompose.utility

enum class RunnerTaskState {

    Scheduled, Cancelled, Failed, Completed;

    val isTerminalState: Boolean get() = this in listOf(Cancelled, Failed, Completed)

}