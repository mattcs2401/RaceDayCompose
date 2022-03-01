package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(
    val meetings: List<Meeting> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)