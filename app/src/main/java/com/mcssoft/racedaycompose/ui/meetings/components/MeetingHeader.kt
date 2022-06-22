package com.mcssoft.racedaycompose.ui.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Meeting

/**
 * Meeting summary information at the top of the list of Races for that Meeting.
 * @param meeting: The Meeting.
 * @param backgroundColour: The background colour.
 *
 * From: https://howtodoandroid.com/jetpack-compose-constraintlayout/
 */
@Composable
fun MeetingHeader(meeting: Meeting, backgroundColour: Color) {

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 4.dp, end = 4.dp)
            .animateContentSize(
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            ),
        shape = RectangleShape,
        backgroundColor = backgroundColour
    ) {
        ConstraintLayout(
            constraintSet
        ) {
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
            IconButton(
                onClick = {
                    expandedState = !expandedState
                },
                Modifier
                    .layoutId("idArrow")
                    .rotate(rotationState)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
        }
        if (expandedState) {
            // Meeting extra info, the 'expanded' state.
            MeetingHeaderExtra(meeting)
        }
    }
}

private val constraintSet = ConstraintSet {
    val idMtgCode = createRefFor("idMtgCode")
    val idMtgCodeText = createRefFor("idMtgCodeText")
    val idVenueName = createRefFor("idVenueName")
    val idVenueNameText = createRefFor("idVenueNameText")
    val idArrow = createRefFor("idArrow")

    constrain(idMtgCode) {
        top.linkTo(parent.top, margin = 8.dp)
        start.linkTo(parent.start, margin = 16.dp)
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
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}