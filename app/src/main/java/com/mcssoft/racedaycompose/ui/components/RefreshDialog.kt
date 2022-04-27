package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * A simple Ok/Cancel dialog.
 */
@Composable
fun RefreshDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirmClicked: () -> Unit,
    onDismissClicked: () -> Unit,
    shape: Shape
) {
    Dialog(onDismissRequest = onDismissClicked) {
        // TODO - parameters for spacing, colour etc ??
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = shape,
            backgroundColor = colors.surface
        ) {
            // A single columns with 3 Rows and Spacers in between.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )

                // 1st Row, dialog title.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = dialogTitle,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth()
                )

                // 2nd Row, dialog text.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = dialogText,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(16.dp)               // 8 dp due to button default height.
                        .fillMaxWidth()
                )

                // 3rd Row, Ok and Cancel buttons.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismissClicked)
                    {
                        Text(text = dismissButtonText)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(onClick = onConfirmClicked)
                    {
                        Text(text = confirmButtonText)
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(8.dp)               // 8 dp due to button default height.
                        .fillMaxWidth()
                )
            }
        }
    }
}
