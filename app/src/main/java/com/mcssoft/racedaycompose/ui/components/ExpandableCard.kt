package com.mcssoft.racedaycompose.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mcssoft.racedaycompose.ui.theme.custom.spacing
import com.mcssoft.racedaycompose.ui.theme.framework.RoundedCornerShapes

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    collapsed: @Composable () -> Unit,
    expanded: @Composable () -> Unit
) {

    var expandedState by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(MaterialTheme.spacing.extraSmall)
        .animateContentSize(
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        ),
        shape = RoundedCornerShapes.small,
        onClick = { expandedState = !expandedState }
    ) {
        collapsed()

        if(expandedState) {
            expanded()
        }
    }
}