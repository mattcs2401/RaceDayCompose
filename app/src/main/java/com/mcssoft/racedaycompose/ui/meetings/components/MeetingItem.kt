package com.mcssoft.racedaycompose.ui.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Meeting

@Composable
fun MeetingItem(meeting: Meeting,
                onItemClick: (Meeting) -> Unit) {

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    var backgroundColour = Color.White

    if(meeting.abandoned) {
        backgroundColour = MaterialTheme.colors.error
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()  // important for placement of the arrow icon.
            .padding(4.dp)
            .animateContentSize(
                animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = backgroundColour
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { onItemClick(meeting) }
        ) {

            val (idMCode, idVenueName, idTime, idArrow) = createRefs()

            Text(meeting.meetingCode, Modifier.constrainAs(idMCode) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            })

            Text(meeting.venueName, Modifier.constrainAs(idVenueName) {
                start.linkTo(idMCode.end, margin = 16.dp)
                top.linkTo(idMCode.top, margin = 0.dp)
            })

            Text(meeting.meetingTime, Modifier.constrainAs(idTime) {
                top.linkTo(idVenueName.top, margin = 0.dp)
                end.linkTo(idArrow.start, margin = 16.dp)
            })

                IconButton(
                    onClick = { expandedState = !expandedState },
                    Modifier
                        .constrainAs(idArrow) {
                            end.linkTo(parent.absoluteRight)
                            centerVerticallyTo(parent)
                        }
                        .rotate(rotationState)
                ) {
                    if(!meeting.abandoned) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Drop-Down Arrow"
                        )
                    }
            }
        }
        if (expandedState) {
            // Meeting extra info.
            MeetingItemR2(meeting, onItemClick)
        }
    }
}

//@Preview
//@Composable
//fun MeetingItemPreview(@PreviewParameter("name") data: com.mcssoft.racedaycompose.data.dummy.Meeting) {
//    MeetingItem(data.meeting, onItemClick = {})
//}
