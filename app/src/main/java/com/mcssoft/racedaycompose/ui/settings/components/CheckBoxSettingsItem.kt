package com.mcssoft.racedaycompose.ui.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mcssoft.racedaycompose.ui.theme.framework.RoundedCornerShapes

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
        //    .padding(spacing.extraSmall),
        shape = RoundedCornerShapes.small,
        backgroundColor = backgroundColour,
        border = BorderStroke(2.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp),
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
                    //fontSize = 18.sp
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
                .fillMaxWidth(0.2f)
                .padding(8.dp),
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