package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
A dialog with a title on the 1st line, and a circular progress indicator and message text on the
2nd line.
@param titleText: The dialog "Title" on the 1st line. e.g. "Loading".
@param msgText: A message to display on the right of a circular progress indicator (2nd line).
 */
@Composable
fun LoadingDialog(
    titleText: String,
    msgText: String
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .border(width = 2.dp, color = Color.Blue),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
            ) {
                Text(titleText)
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.3f),
                ) {
                    CircularProgressIndicator()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f),
                ) {
                    Text(msgText, fontSize = 12.sp, overflow = TextOverflow.Ellipsis, maxLines = 1)
                }
            }

        }
    }
}

