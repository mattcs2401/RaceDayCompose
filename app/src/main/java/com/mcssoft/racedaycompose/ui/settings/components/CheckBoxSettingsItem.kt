package com.mcssoft.racedaycompose.ui.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.mcssoft.racedaycompose.ui.theme.*

@Composable
fun CheckBoxSettingsItem(
    textTitle: String,
    textDescription: String,
    selected: Boolean,
    onChange: (Boolean) -> Unit,
    backgroundColour: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour,
        border = BorderStroke(
            width = stroke2dp,
            color = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(eightyPercent)
                .padding(padding8dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = textTitle,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = textDescription
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(twentyPercent)
                .padding(padding8dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Checkbox(
                checked = selected,
                onCheckedChange = onChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    checkmarkColor = MaterialTheme.colors.onBackground
                )
            )
        }

    }
}