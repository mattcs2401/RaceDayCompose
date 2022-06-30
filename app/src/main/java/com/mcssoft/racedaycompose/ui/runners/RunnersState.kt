package com.mcssoft.racedaycompose.ui.runners

import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner

/**
 * Class to hold the state of the RunnersScreen.
 * @param exception: An exception (if thrown).
 * @param status: Loading, Failure, Success.
 * @param loading: Data is being collected.
 * @param lRunners: The list of Runners to display (associated with the Race).
 * @param race: The Race associated with the Runners listing (used simply for header info).
 * @param raceId: The id of the Race (passed in the nav args).
 */
data class RunnersState(
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var lRunners: List<Runner> = emptyList(),
    var race: Race?,
    var raceId: Long = 0          // passed in the nav args and used to get Race and Runner info.
) {
    companion object {
        fun initialise(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Initialise,
                race = null
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