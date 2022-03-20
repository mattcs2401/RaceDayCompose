package com.mcssoft.racedaycompose.domain.dto

import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingDto(
    val Abandoned: Boolean,
    val MeetingCode: String,
    val MeetingId: Int,
    val MeetingType: String,
    val Races: List<RaceDto>,
    val VenueName: String

//    Not used ATT.
//    val WeatherChanged: Boolean
//    val BoxChallengeAvailable: Boolean,
//    val DriverChallengeAvailable: Boolean,
//    val JockeyChallengeAvailable: Boolean,
//    val Pools: List<PoolDto>,
//    val TrackChanged: Boolean,
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
