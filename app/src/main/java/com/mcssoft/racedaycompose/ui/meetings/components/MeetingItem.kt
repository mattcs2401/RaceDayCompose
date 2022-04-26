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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun MeetingItem(meeting: Meeting,
                onItemClick: (Meeting) -> Unit) {

    val backgroundColour = if(meeting.abandoned) {
        MaterialTheme.colors.error
    } else {
        MaterialTheme.colors.primaryVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()  // important for placement of the arrow icon.
            .padding(MaterialTheme.spacing.extraSmall)
            .animateContentSize(
                animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing)
            ),
        shape = RoundedCornerShape(MaterialTheme.spacing.small),
        elevation = 4.dp,
        backgroundColor = backgroundColour
    ) {
        MeetingItemR1(meeting = meeting, onItemClick = onItemClick)
    }
}


