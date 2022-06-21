package com.mcssoft.racedaycompose.ui.components.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mcssoft.racedaycompose.ui.theme.*

/**
A dialog with a title on the 1st line, and a circular progress indicator and message text on the
2nd line.
@param titleText: The dialog "Title" on the 1st line. e.g. "Loading".
@param msgText: A message to display on the right of a circular progress indicator (2nd line).
 */
@Composable
fun LoadingDialog(
    titleText: String,
    msgText: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(eightyPercent)
                .border(
                    width = width2dp,
                    color = Color.Blue
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.padding(
                    start = padding16dp,
                    top = padding16dp,
                    bottom = padding8dp
                )
            ) {
                Text(titleText)
            }
            Row(
                modifier = Modifier.padding(
                    start = padding16dp,
                    top = padding8dp,
                    bottom = padding16dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(thirtyPercent),
                ) {
                    CircularProgressIndicator()
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(seventyPercent),
                ) {
                    Text(
                        msgText,
                        fontSize = fontSize12sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

