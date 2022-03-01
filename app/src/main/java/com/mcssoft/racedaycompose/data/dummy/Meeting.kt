package com.mcssoft.racedaycompose.data.dummy

/**
 * Data for @Previews.
 */
object Meeting {

    var meeting: Meeting = Meeting(
        abandoned = false,
        meetingCode = "SR",
        venueName = "Port Maquarie",
        meetingTime = "12:05",
        numRaces = 8,
        weatherCond = "Showery",
        trackCond = "Heavy",
        trackRating = 5
    )

    data class Meeting(
        val abandoned: Boolean,
        val meetingCode: String,
        val venueName: String,
        val meetingTime: String,
        val numRaces: Int,
        val weatherCond: String,
        val trackCond: String,
        val trackRating: Int
    )

}
