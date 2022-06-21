package com.mcssoft.racedaycompose.ui.races

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race

/**
 * Class to hold the state of the RacesScreen.
 * @param exception: An exception (if thrown).
 * @param status: Loading, Failure, Success.
 * @param loading: Data is being collected.
 * @param lRaces: The list of Races to display (associated with the Meeting).
 * @param mtg: The Meeting associated with the Races listing (used simply for header info).
 * @param mtgId: The meeting id of the associated meeting. Kept separate as it's used to retrieve
 *               the Meeting on back nav to the Races screen.
 */
data class RacesState(
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var lRaces: List<Race> = emptyList(),
    var mtg: Meeting?,
    var mtgId: Long = 0
) {
    companion object {
        fun initialise(): RacesState {
            return RacesState(
                exception = null,
                status = Status.Initialise,
                mtg = null,
            )
        }
    }

    sealed class Status {
        object Initialise : Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    val races: List<Race>
        get() = this.lRaces

    val meeting: Meeting?
        get() = this.mtg
}