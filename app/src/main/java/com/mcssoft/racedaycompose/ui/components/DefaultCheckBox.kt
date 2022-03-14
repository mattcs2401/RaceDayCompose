package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCheckBox(
    text: String,
    selected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    // A Row with two columns.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(
                onClick = {
                    onCheckedChange(!selected)
                })
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column one, the text for the checkbox label.
        Column(modifier = Modifier
            .fillMaxWidth(0.4f),
            horizontalAlignment = Alignment.Start) {
            Text(text = text, style = MaterialTheme.typography.body1)
        }
        // Column two, the checkbox.
        Column(modifier = Modifier
            .fillMaxWidth(0.6f),
            horizontalAlignment = Alignment.Start) {
            Checkbox(
                checked = selected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    checkmarkColor = MaterialTheme.colors.onBackground
                )
            )
        }
    }
}
