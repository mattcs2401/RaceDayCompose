package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(

    /** Note: These need to be initialised. **/

    var error: String = "",                      // an error or some other related message.
    var loading: Boolean = false,              // retrieve from Api, save to database etc.
    var meetings: List<Meeting> = emptyList(),   // a list of Meetings.

//    val date: String = "",             // TBA.
//    val useFromDb: Boolean = false     // TBA.
)