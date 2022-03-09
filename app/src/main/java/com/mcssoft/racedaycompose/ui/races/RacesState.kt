package com.mcssoft.racedaycompose.ui.races

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race

/**
 * Class to hold the state of the RacesScreen.
 * @param loading: Data is being collected.
 * @param error: An error has occurred and the message is here.
 * @param races: The list of Races to display (associated with the Meeting).
 * @param meeting: The Meeting associated with the Races listing (used simply for header info).
 */
data class RacesState(
    var loading: Boolean = false,           // a loading state flag.
    var error: String = "",                 // a string for error messages.
    var races: List<Race> = emptyList(),    // the list of Races.
    var meeting: Meeting? = null,           // the Meeting associated with the Races listing.
)