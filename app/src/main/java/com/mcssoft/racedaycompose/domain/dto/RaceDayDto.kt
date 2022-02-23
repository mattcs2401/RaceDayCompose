package com.mcssoft.racedaycompose.domain.dto

data class RaceDayDto(
    val CurrentDay: Boolean,
    val MeetingDate: String,
    val Meetings: List<MeetingDto>
)
