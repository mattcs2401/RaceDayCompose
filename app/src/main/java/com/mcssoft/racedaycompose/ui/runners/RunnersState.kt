package com.mcssoft.racedaycompose.ui.runners

import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner

/**
 * Class to hold the state of the RacesScreen.
 * @param loading: Data is being collected.
 * @param error: An error has occurred and the message is here.
 * @param runners: The list of Runners to display (associated with the Race).
 * @param race: The Race associated with the Runners listing (used simply for header info).
 */
data class RunnersState(
    var loading: Boolean = false,                // a loading state flag.
    var error: String = "",                      // a string for error messages.
    var runners: List<Runner> = emptyList(),     // the list of Runners.
    var race: Race? = null,                      // the Race associated with the Runners listing.
)