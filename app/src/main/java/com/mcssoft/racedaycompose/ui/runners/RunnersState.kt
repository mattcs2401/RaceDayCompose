package com.mcssoft.racedaycompose.ui.runners

import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner

/**
 * Class to hold the state of the RunnersScreen.
 * @param exception: An exception (if thrown).
 * @param status: Loading, Failure, Success.
 * @param loading: Data is being collected.
 * @param lRunners: The list of Runners to display (associated with the Race).
 * @param rce: The Race associated with the Runners listing (used simply for header info).
 */
data class RunnersState(
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var lRunners: List<Runner> = emptyList(),
    var rce: Race?,
    var rceId: Long = 0
) {
    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                rce = null
            )
        }
    }

    sealed class Status {
        object Initialise: Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    val runners: List<Runner>
        get() = this.lRunners

    val race: Race?
        get() = this.rce

}