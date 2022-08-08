package com.mcssoft.racedaycompose.ui.meetings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layoutId
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaycompose.ui.theme.margin0dp
import com.mcssoft.racedaycompose.ui.theme.margin16dp
import com.mcssoft.racedaycompose.ui.theme.padding4dp

@Composable
fun MeetingItem(
    meeting: Meeting,
    onItemClick: (Meeting) -> Unit,
) {
    val backgroundColour = if (meeting.abandoned) {
        MaterialTheme.colors.error
    } else {
        MaterialTheme.colors.primaryVariant
    }

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp)
            .animateContentSize(
                animationSpec = tween(300, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour
    ) {
        // Initial display of Meeting details.
        ConstraintLayout(
            constraintSet,
            modifier = Modifier
                .clickable { onItemClick(meeting) }
        ) {
            Text(
                meeting.meetingCode,
                Modifier.layoutId("idMCode")
            )
            Text(
                meeting.venueName,
                Modifier.layoutId("idVenueName")
            )
            Text(
                meeting.meetingTime,
                Modifier.layoutId("idTime")
            )
            IconButton(
                onClick = {
                    expandedState = !expandedState
                },
                Modifier
                    .layoutId("idArrow")
                    .rotate(rotationState)
            ) {
                if (!meeting.abandoned) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
        }
        if (expandedState) {
            // Meeting extra info, the 'expanded' state.
            MeetingItemExtra(meeting, onItemClick)
        }
    }

}

private val constraintSet = ConstraintSet {
    val idMCode = createRefFor("idMCode")
    val idVenueName = createRefFor("idVenueName")
    val idTime = createRefFor("idTime")
    val idArrow = createRefFor("idArrow")

    constrain(idMCode) {
        top.linkTo(parent.top, margin = margin16dp)
        start.linkTo(parent.start, margin = margin16dp)
    }
    constrain(idVenueName) {
        start.linkTo(idMCode.end, margin = margin16dp)
        top.linkTo(idMCode.top, margin = margin0dp)
    }
    constrain(idTime) {
        top.linkTo(idVenueName.top, margin = margin0dp)
        end.linkTo(idArrow.start, margin = margin16dp)
    }
    constrain(idArrow) {
        end.linkTo(parent.absoluteRight)
        centerVerticallyTo(parent)
    }
}