package com.mcssoft.racedaycompose.ui.races

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race

data class RacesState(
    val races: List<Race> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    var mId: Long = 0
)