package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Meeting

@Composable
fun MeetingItemR2(meeting: Meeting,
                  onItemClick: (Meeting) -> Unit) {

    ConstraintLayout(
        modifier = Modifier
            .padding(top = 48.dp) // simply to give room for the top row.
            .clickable { onItemClick(meeting) },

    ) {

        val (idAbandoned, idRacesNo, idWeatherCond, idTrackCond, idTrackeRating) = createRefs()

        Text("Abandoned: ${if(meeting.abandoned) "Y" else "N"}",
            Modifier.constrainAs(idAbandoned) {
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(parent.top, margin = 0.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            },
            fontSize = 12.sp

        )

        Text("Races: ${meeting.racesNo}",
            Modifier.constrainAs(idRacesNo) {
                start.linkTo(idAbandoned.end, margin = 16.dp)
                top.linkTo(idAbandoned.top, margin = 0.dp)
            },
            fontSize = 12.sp
        )

        Text(meeting.weatherCond,
            Modifier.constrainAs(idWeatherCond) {
                start.linkTo(idRacesNo.end, margin = 32.dp)
                top.linkTo(idRacesNo.top, margin = 0.dp)
            },
            fontSize = 12.sp
        )

        Text(meeting.trackCond,
            Modifier.constrainAs(idTrackCond) {
                start.linkTo(idWeatherCond.end, margin = 8.dp)
                top.linkTo(idWeatherCond.top, margin = 0.dp)
            },
            fontSize = 12.sp
        )

        Text(meeting.trackRating.toString(),
            Modifier.constrainAs(idTrackeRating) {
                start.linkTo(idTrackCond.end, margin = 8.dp)
                top.linkTo(idTrackCond.top, margin = 0.dp)
            },
            fontSize = 12.sp
        )

    }
}

//@Preview
//@Composable
//fun MeetingItemR2Preview() {
//    MeetingItemR2(meeting, onItemClick = {})
//}