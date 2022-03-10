package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun RefreshDialog(
    onConfirmClicked: () -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
//        Surface(
//            shape = MaterialTheme.shapes.medium,
//            color = MaterialTheme.colors.surface,
//        ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.extraSmall),
        shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface

    ) {
            ConstraintLayout(modifier = Modifier.fillMaxWidth()
            ) {
                val (idTitle, idText, idOkBtn, idCancelBtn) = createRefs()

                Text(text = "Refresh Data",
                    modifier = Modifier.constrainAs(idTitle) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                    },
                    fontWeight = FontWeight.Bold
                )

                Text(text = "Delete all backing data and recreate ?",
                    modifier = Modifier.constrainAs(idText) {
                        top.linkTo(idTitle.bottom, margin = 16.dp)
                        start.linkTo(idTitle.start, margin = 0.dp)
                        end.linkTo(idTitle.end, margin = 0.dp)
                    }
                )

                TextButton(onClick = onConfirmClicked,
                modifier = Modifier.constrainAs(idOkBtn) {
                    top.linkTo(idText.bottom, margin = 32.dp)
                    start.linkTo(idText.start, margin = 0.dp)
                }) {
                    Text(text = "OK")
                }

                TextButton(onClick = onDismiss,
                    modifier = Modifier.constrainAs(idCancelBtn) {
                        top.linkTo(idOkBtn.top, margin = 0.dp)
                        start.linkTo(idOkBtn.end, margin = 32.dp)
                    }) {
                    Text(text = "CANCEL")
                }
            }

        }
    }
}