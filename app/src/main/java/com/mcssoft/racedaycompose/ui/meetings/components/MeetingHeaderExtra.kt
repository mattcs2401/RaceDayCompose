package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Meeting

/**
 * Additional display of Meeting details (the 'expanded' state).
 * @param meeting: The Meeting to get details from for display.
 */
@Composable
fun MeetingHeaderExtra(
    meeting: Meeting
) {
    ConstraintLayout(
        constraintSet,
        modifier = Modifier
            .padding(top = 40.dp)
    ) {
        Text(
            "Abandoned: ${ if (meeting.abandoned) "Y" else "N" }",
            Modifier.layoutId("idAbandoned"),
            fontSize = 12.sp
        )
        Text(
            "Races: ${meeting.racesNo}",
            Modifier.layoutId("idRacesNo"),
            fontSize = 12.sp
        )
        Text(
            meeting.weatherCond,
            Modifier.layoutId("idWeatherCond"),
            fontSize = 12.sp
        )
        Text(
            meeting.trackCond,
            Modifier.layoutId("idTrackCond"),
            fontSize = 12.sp
        )
        if (meeting.trackRating > 0) {
            Text(
                meeting.trackRating.toString(),
                Modifier.layoutId("idTrackRating"),
                fontSize = 12.sp
            )
        }
    }
}

private val constraintSet = ConstraintSet {
    val idAbandoned = createRefFor("idAbandoned")
    val idRacesNo = createRefFor("idRacesNo")
    val idWeatherCond = createRefFor("idWeatherCond")
    val idTrackCond = createRefFor("idTrackCond")
    val idTrackRating = createRefFor("idTrackRating")

    constrain(idAbandoned) {
        start.linkTo(parent.start, margin = 16.dp)
        top.linkTo(parent.top, margin = 0.dp)
        bottom.linkTo(parent.bottom, margin = 16.dp)
    }
    constrain(idRacesNo) {
        start.linkTo(idAbandoned.end, margin = 16.dp)
        top.linkTo(idAbandoned.top, margin = 0.dp)
    }
    constrain(idWeatherCond) {
        start.linkTo(idRacesNo.end, margin = 32.dp)
        top.linkTo(idRacesNo.top, margin = 0.dp)
    }
    constrain(idTrackCond) {
        start.linkTo(idWeatherCond.end, margin = 8.dp)
        top.linkTo(idWeatherCond.top, margin = 0.dp)
    }
    constrain(idTrackRating) {
        start.linkTo(idTrackCond.end, margin = 8.dp)
        top.linkTo(idTrackCond.top, margin = 0.dp)
    }
}