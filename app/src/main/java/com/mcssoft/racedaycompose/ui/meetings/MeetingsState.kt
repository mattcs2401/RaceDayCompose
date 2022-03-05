package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(

    /** Note: These need to be initialised. **/

    val error: String = "",                      // an error message.
    val saved: Boolean = false,                  // Meeting/Race data saved to the database.
    val raw: RaceDayDto? = null,                 // the raw data from the Api.
    val isLoading: Boolean = false,              // retrieve from Api, save to database etc.
    val meetings: List<Meeting> = emptyList(),   // a list of Meetings.

    val date: String = "",             // TBA.
    val useFromDb: Boolean = false     // TBA.
)