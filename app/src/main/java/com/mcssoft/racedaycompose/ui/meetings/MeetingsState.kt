package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.RaceDay
import kotlinx.coroutines.flow.Flow

data class MeetingsState(
    val meetings: List<Meeting> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)