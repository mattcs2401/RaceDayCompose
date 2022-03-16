package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun Loading(text: String = "") {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        /*
          Note: Done as constraint layout as example. Could also be done; Box with a column and two
                rows in the column.
         */
        val (idCPInd, txtLoading) = createRefs()

        CircularProgressIndicator(
            Modifier.constrainAs(idCPInd) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            color = MaterialTheme.colors.primary
        )
        if(text != "") {
            Text(
                text,
                Modifier.constrainAs(txtLoading) {
                    top.linkTo(idCPInd.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                color = MaterialTheme.colors.primary
            )
        }
    }
}
