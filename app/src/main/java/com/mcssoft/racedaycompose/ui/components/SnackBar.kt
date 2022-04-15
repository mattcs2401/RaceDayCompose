package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun SnackBar(
    snackBarHostState: SnackbarHostState
) {
    // TODO("Tie in with scaffold ?")
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val idSnackBar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(idSnackBar) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            hostState = snackBarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackBarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                text = snackBarHostState.currentSnackbarData?.actionLabel ?: "Unknown",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(snackBarHostState.currentSnackbarData?.message ?: "Unknown message")
                }
            }
        )
    }
}
