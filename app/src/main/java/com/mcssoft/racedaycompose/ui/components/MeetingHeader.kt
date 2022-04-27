package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Meeting

@Composable
        /**
         * Meeting summary information at the top of the list of Races for that Meeting.
         * @param meeting: The Meeting.
         * @param colour: The background colour.
         *
         * From: https://howtodoandroid.com/jetpack-compose-constraintlayout/
         */
fun MeetingHeader(meeting: Meeting, colour: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colour)
            .border(width = 2.dp, color = Color.Blue)
    )
    {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {
            /* Top line. */

            Text(
                text = "Meeting: ",
                Modifier.layoutId("idMtgCode")
            )

            Text(
                text = meeting.meetingCode,
                Modifier.layoutId("idMtgCodeText")
            )

            Text(
                text = "Venue: ",
                Modifier.layoutId("idVenueName")
            )

            Text(
                text = meeting.venueName,
                Modifier.layoutId("idVenueNameText")
            )

            /* Bottom line. */

            Text(
                text = "Weather: ",
                Modifier.layoutId("idWeather"),
                fontSize = 12.sp
            )

            Text(
                text = meeting.weatherCond,
                Modifier.layoutId("idWeatherText"),
                fontSize = 12.sp
            )

            Text(
                text = "Track: ",
                Modifier.layoutId("idTrack"),
                fontSize = 12.sp
            )

            Text(
                text = meeting.trackCond,
                Modifier.layoutId("idTrackText"),
                fontSize = 12.sp
            )

            Text(
                text = meeting.trackRating.toString(),
                Modifier.layoutId("idRatingText"),
                fontSize = 12.sp
            )
        }
    }
}

val constraintSet = ConstraintSet {
    val idMtgCode = createRefFor("idMtgCode")
    val idMtgCodeText = createRefFor("idMtgCodeText")
    val idVenueName = createRefFor("idVenueName")
    val idVenueNameText = createRefFor("idVenueNameText")
    val idWeather = createRefFor("idWeather")
    val idWeatherText = createRefFor("idWeatherText")
    val idTrack = createRefFor("idTrack")
    val idTrackText = createRefFor("idTrackText")
    val idRatingText = createRefFor("idRatingText")

    constrain(idMtgCode) {
        top.linkTo(parent.top, margin = 8.dp)
        start.linkTo(parent.start, margin = 8.dp)
    }
    constrain(idMtgCodeText) {
        top.linkTo(idMtgCode.top, margin = 0.dp)
        start.linkTo(idMtgCode.end, margin = 4.dp)
    }
    constrain(idVenueName) {
        top.linkTo(idMtgCodeText.top, margin = 0.dp)
        start.linkTo(idMtgCodeText.end, margin = 16.dp)
    }
    constrain(idVenueNameText) {
        top.linkTo(idVenueName.top, margin = 0.dp)
        start.linkTo(idVenueName.end, margin = 4.dp)
    }
    constrain(idWeather) {
        top.linkTo(idMtgCode.bottom, margin = 8.dp)
        start.linkTo(parent.start, margin = 8.dp)
        bottom.linkTo(parent.bottom, margin = 8.dp)
    }
    constrain(idWeatherText) {
        top.linkTo(idWeather.top, margin = 0.dp)
        start.linkTo(idWeather.end, margin = 4.dp)
    }
    constrain(idTrack) {
        top.linkTo(idWeatherText.top, margin = 0.dp)
        start.linkTo(idWeatherText.end, margin = 16.dp)
    }
    constrain(idTrackText) {
        top.linkTo(idTrack.top, margin = 0.dp)
        start.linkTo(idTrack.end, margin = 4.dp)
    }
    constrain(idRatingText) {
        top.linkTo(idTrackText.top, margin = 0.dp)
        start.linkTo(idTrackText.end, margin = 4.dp)
    }
}