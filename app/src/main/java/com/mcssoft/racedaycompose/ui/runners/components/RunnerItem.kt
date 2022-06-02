package com.mcssoft.racedaycompose.ui.runners.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Runner
import com.mcssoft.racedaycompose.ui.theme.*

@Composable
fun RunnerItem(
    runner: Runner,
    onCheckedChange: (Boolean) -> Unit
) {
    // Checkbox checked state.
    val checkedState = remember { mutableStateOf(runner.checked) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp
        //backgroundColor = Col
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { }//onCheckedChange(runner) }
        ) {
            val (idRunnerNo,
                idRunnerName,
                idLast3,
                idForm,
                idBarrier,
                idJockey,
                idRating,
                idWeight,
                idCb) = createRefs()

            /* First row. */

            Text(
                runner.runnerNumber.toString(),
                Modifier.constrainAs(idRunnerNo) {
                    top.linkTo(parent.top, margin = margin8dp)
                    start.linkTo(parent.start, margin = margin8dp)
                }, fontSize = fontSize12sp
            )

            Text(
                runner.runnerName,
                Modifier.constrainAs(idRunnerName) {
                    top.linkTo(idRunnerNo.top, margin = margin0dp)
                    start.linkTo(idRunnerNo.end, margin = margin16dp)
                }, fontSize = fontSize12sp
            )

            Text(
                "L3: ${runner.lastThreeStarts}",
                Modifier.constrainAs(idLast3) {
                    top.linkTo(idRunnerName.top, margin = margin0dp)
                    start.linkTo(idRunnerName.end, margin = margin16dp)
                }, fontSize = fontSize12sp
            )

            Text(
                "F: ${runner.form}",
                Modifier.constrainAs(idForm) {
                    top.linkTo(idLast3.top, margin = margin0dp)
                    start.linkTo(idLast3.end, margin = margin8dp)
                }, fontSize = fontSize12sp
            )

            Text(
                "B: ${runner.barrier}",
                Modifier.constrainAs(idBarrier) {
                    top.linkTo(idForm.top, margin = margin0dp)
                    start.linkTo(idForm.end, margin = margin8dp)
                }, fontSize = fontSize12sp
            )

            Text(
                "R: ${runner.rating}",
                Modifier.constrainAs(idRating) {
                    top.linkTo(idBarrier.top, margin = margin0dp)
                    start.linkTo(idBarrier.end, margin = margin8dp)
                }, fontSize = fontSize12sp
            )

            /* 2nd row. */

            Text(
                runner.riderName,
                Modifier.constrainAs(idJockey) {
                    top.linkTo(idRunnerName.bottom, margin = margin8dp)
                    start.linkTo(idRunnerName.start, margin = margin0dp)
                    bottom.linkTo(parent.bottom, margin = margin8dp)
                }, fontSize = fontSize12sp
            )

            Text(
                "${runner.weight}kg",
                Modifier.constrainAs(idWeight) {
                    top.linkTo(idJockey.top, margin = margin0dp)
                    start.linkTo(idJockey.end, margin = margin16dp)
                }, fontSize = fontSize12sp
            )

            /* Checkbox. */
            Checkbox(
                checked = checkedState.value,// runner.checked,
                onCheckedChange = { checked ->
                    checkedState.value = checked
                    onCheckedChange(checked)
                },
                Modifier.constrainAs(idCb) {
                    top.linkTo(parent.top, margin = margin8dp)
                    end.linkTo(parent.end, margin = margin8dp)
                },
                enabled = true
            )


        }
    }
}