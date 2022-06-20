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
    var lRunners: List<Runner> = emptyList(),     // the list of Runners.
    var rce: Race? = null,                      // the Race associated with the Runners listing.
) {
    companion object {
        fun success(race: Race, runners: List<Runner>): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Success,
                loading = false,
                lRunners = runners,
                rce = race,
            )
        }

        fun failure(exception: Exception): RunnersState {
            return RunnersState(
                exception = exception,
                status = Status.Failure,
                loading = false,
                lRunners = emptyList(),
                rce = null,
            )
        }

        fun loading(): RunnersState {
            return RunnersState(
                exception = null,
                status = Status.Loading,
                loading = true,
                lRunners = emptyList(),
                rce = null,
            )
        }
    }

    sealed class Status {
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    //    val loading: Boolean
//        get() = this.status == RunnersState.Status.Loading

    val failed: Boolean
        get() = this.status == Status.Failure

    val successful: Boolean
        get() = this.status == Status.Success //(!failed) && (this.data != null) && (this.data?.isNotEmpty() == true)

    val runners: List<Runner>
        get() = this.lRunners ?: emptyList()

    val race: Race?
        get() = this.rce
}