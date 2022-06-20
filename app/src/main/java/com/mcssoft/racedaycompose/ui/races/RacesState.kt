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
 *               the Meeting.
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

        fun success(meeting: Meeting, races: List<Race>): RacesState {
            return RacesState(
                exception = null,
                status = Status.Success,
                loading = false,
                lRaces = races,
                mtg = meeting,
                mtgId = meeting._id,
            )
        }

        fun failure(exception: Exception): RacesState {
            return RacesState(
                exception = exception,
                status = Status.Failure,
                loading = false,
                lRaces = emptyList(),
                mtg = null,
            )
        }

        fun loading(): RacesState {
            return RacesState(
                exception = null,
                status = Status.Loading,
                loading = true,
                lRaces = emptyList(),
                mtg = null,
            )
        }
    }

    sealed class Status {
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

//    val loading: Boolean
//        get() = this.status == RacesState.Status.Loading

    val failed: Boolean
        get() = this.status == RacesState.Status.Failure

    val successful: Boolean
        get() = this.status == RacesState.Status.Success //(!failed) && (this.data != null) && (this.data?.isNotEmpty() == true)

    val races: List<Race>
        get() = this.lRaces ?: emptyList()

    val meeting: Meeting?
        get() = this.mtg
}