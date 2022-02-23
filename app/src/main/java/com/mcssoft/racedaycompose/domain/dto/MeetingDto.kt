package com.mcssoft.racedaycompose.domain.dto

import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingDto(
    val Abandoned: Boolean,
//    val BoxChallengeAvailable: Boolean,
//    val DriverChallengeAvailable: Boolean,
//    val JockeyChallengeAvailable: Boolean,
    val MeetingCode: String,
    val MeetingId: Int,
    val MeetingType: String,
//    val Pools: List<PoolDto>,
    val Races: List<RaceDto>,
//    val TrackChanged: Boolean,
    val VenueName: String
//    val WeatherChanged: Boolean
)

fun MeetingDto.toMeeting(): Meeting {
    return Meeting(
        abandoned = Abandoned,
        meetingCode = MeetingCode,
        meetingId = MeetingId,
        meetingType = MeetingType,
        venueName = VenueName
    )
}
