package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(

    /** Note: These need to be initialised. **/
    var error: String = "",
    var loading: Boolean = false,
    var meetings: List<Meeting> = emptyList(),

//    var error: ErrorType? = null,
)
//{
//    sealed class ErrorType {
//        class ExceptionError(): Error()
//        object UnknownError: Error()
//    }
//}