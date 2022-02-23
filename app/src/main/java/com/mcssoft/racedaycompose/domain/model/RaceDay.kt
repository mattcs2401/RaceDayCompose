package com.mcssoft.racedaycompose.domain.model

data class RaceDay(
    val currentDay: Boolean,
    val meetingDate: String,
    val meetings: List<Meeting>
)