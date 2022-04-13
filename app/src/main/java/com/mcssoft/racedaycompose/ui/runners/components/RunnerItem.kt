package com.mcssoft.racedaycompose.ui.runners.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Runner

@Composable
fun RunnerItem(
    runner: Runner,
    onItemClick: (Runner) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
        //backgroundColor = Col
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { onItemClick(runner) }
        ) {




        }
    }
}