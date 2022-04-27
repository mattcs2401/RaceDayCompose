package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CheckBox(
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
                }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column one, the text for the checkbox label.
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.4f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = text, style = MaterialTheme.typography.body1)
        }
        // Column two, the checkbox.
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.6f),
            horizontalAlignment = Alignment.Start
        ) {
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
