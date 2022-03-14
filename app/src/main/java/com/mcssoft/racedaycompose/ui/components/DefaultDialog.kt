package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

/**
 * A simple Ok/Cancel dialog.
 */
@Composable
fun DefaultDialog(
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirmClicked: () -> Unit,
    onDismissClicked: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissClicked) {
    // TODO - parameters for shape, spacing, colour etc ?
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(MaterialTheme.spacing.small),
            backgroundColor = MaterialTheme.colors.surface
        ){
            // A single columns with 3 Rows and Spacers in between.
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {

                // Ist Row, dialog title.
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = dialogTitle,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth()
                )

                // 2nd Row, dialog text.
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = dialogText,
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier
                    .height(8.dp)               // 8 dp due to button default height.
                    .fillMaxWidth()
                )

                // 3rd Row, Ok and Cancel buttons.
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onConfirmClicked) {
                        Text(text = confirmButtonText)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = onDismissClicked) {
                        Text(text = dismissButtonText)
                    }
                }
            }
        }
    }
}
