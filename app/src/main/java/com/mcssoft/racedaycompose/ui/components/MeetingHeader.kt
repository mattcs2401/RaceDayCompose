package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun MeetingHeader(meeting: Meeting, colour: Color) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(MaterialTheme.spacing.extraSmall),
//        shape = RoundedCornerShape(MaterialTheme.spacing.default),
//        elevation = 4.dp,
//        backgroundColor = colour
//
//    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colour)
            .border(width = 2.dp, color = Color.Blue)
    )
    {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (idMtgCode,
                idMtgCodeText,
                idVenueName,
                idVenueNameText,
                idWeather,
                idWeatherText,
                idTrack,
                idTrackText,
                idRatingText) = createRefs()

            /* Top line. */

            Text(text = "Meeting: ",
                Modifier.constrainAs(idMtgCode) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }
            )

            Text(text = meeting.meetingCode,
                Modifier.constrainAs(idMtgCodeText) {
                    top.linkTo(idMtgCode.top, margin = 0.dp)
                    start.linkTo(idMtgCode.end, margin = 4.dp)
                }
            )

            Text(text = "Venue: ",
                Modifier.constrainAs(idVenueName) {
                    top.linkTo(idMtgCodeText.top, margin = 0.dp)
                    start.linkTo(idMtgCodeText.end, margin = 16.dp)
                }
            )

            Text(text = meeting.venueName,
                Modifier.constrainAs(idVenueNameText) {
                    top.linkTo(idVenueName.top, margin = 0.dp)
                    start.linkTo(idVenueName.end, margin = 4.dp)
                }
            )

            /* Bottom line. */

            Text(text = "Weather: ",
                Modifier.constrainAs(idWeather) {
                    top.linkTo(idMtgCode.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text(text = meeting.weatherCond,
                Modifier.constrainAs(idWeatherText) {
                    top.linkTo(idWeather.top, margin = 0.dp)
                    start.linkTo(idWeather.end, margin = 4.dp)
                }, fontSize = 12.sp
            )

            Text(text = "Track: ",
                Modifier.constrainAs(idTrack) {
                    top.linkTo(idWeatherText.top, margin = 0.dp)
                    start.linkTo(idWeatherText.end, margin = 16.dp)
                }, fontSize = 12.sp
            )

            Text(text = meeting.trackCond,
                Modifier.constrainAs(idTrackText) {
                    top.linkTo(idTrack.top, margin = 0.dp)
                    start.linkTo(idTrack.end, margin = 4.dp)
                }, fontSize = 12.sp
            )

            Text(text = meeting.trackRating.toString(),
                Modifier.constrainAs(idRatingText) {
                    top.linkTo(idTrackText.top, margin = 0.dp)
                    start.linkTo(idTrackText.end, margin = 4.dp)
                }, fontSize = 12.sp
            )


        }
    }
}