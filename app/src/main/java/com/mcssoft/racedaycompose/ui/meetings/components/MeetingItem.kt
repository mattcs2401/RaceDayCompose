package com.mcssoft.racedaycompose.ui.meetings.components

import androidx.compose.material.*
import androidx.compose.runtime.*
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.ui.components.ExpandableCard
import com.mcssoft.racedaycompose.ui.theme.framework.RoundedCornerShapes

@ExperimentalMaterialApi
@Composable
fun MeetingItem(meeting: Meeting,
                onItemClick: (Meeting) -> Unit) {

    val backgroundColour = if(meeting.abandoned) {
        MaterialTheme.colors.error
    } else {
        MaterialTheme.colors.primaryVariant
    }

    ExpandableCard(
        collapsed = {
            MeetingItemR1(meeting = meeting, onItemClick = onItemClick)
        },
        expanded = {
            MeetingItemR2(meeting = meeting, onItemClick = onItemClick)
        },
        duration = 300,
        shape = RoundedCornerShapes.small,
        backgroundColour = backgroundColour
    )

}


