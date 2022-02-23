package com.mcssoft.racedaycompose.domain.dto

data class BaseDto(
    val ErrorInfo: Any,
    val RaceDay: RaceDayDto,
    val Success: Boolean
)