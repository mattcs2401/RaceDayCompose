package com.mcssoft.racedaycompose.ui.runners.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Runner

@Composable
fun RunnerItem(
    runner: Runner,
    onCheckedChange: (Boolean) -> Unit
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

            Text(runner.runnerNumber.toString(),
                Modifier.constrainAs(idRunnerNo) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text(runner.runnerName,
                Modifier.constrainAs(idRunnerName) {
                    top.linkTo(idRunnerNo.top, margin = 0.dp)
                    start.linkTo(idRunnerNo.end, margin = 16.dp)
                }, fontSize = 12.sp
            )

            Text("L3: ${runner.lastThreeStarts}",
                Modifier.constrainAs(idLast3) {
                    top.linkTo(idRunnerName.top, margin = 0.dp)
                    start.linkTo(idRunnerName.end, margin = 16.dp)
                }, fontSize = 12.sp
            )

            Text("F: ${runner.form}",
                Modifier.constrainAs(idForm) {
                    top.linkTo(idLast3.top, margin = 0.dp)
                    start.linkTo(idLast3.end, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text("B: ${runner.barrier}",
                Modifier.constrainAs(idBarrier) {
                    top.linkTo(idForm.top, margin = 0.dp)
                    start.linkTo(idForm.end, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text("R: ${runner.rating}",
                Modifier.constrainAs(idRating) {
                    top.linkTo(idBarrier.top, margin = 0.dp)
                    start.linkTo(idBarrier.end, margin = 8.dp)
                }, fontSize = 12.sp
            )

            /* 2nd row. */

            Text(runner.riderName,
                Modifier.constrainAs(idJockey) {
                    top.linkTo(idRunnerName.bottom, margin = 8.dp)
                    start.linkTo(idRunnerName.start, margin = 0.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text("${runner.weight}kg",
                Modifier.constrainAs(idWeight) {
                    top.linkTo(idJockey.top, margin = 0.dp)
                    start.linkTo(idJockey.end, margin = 16.dp)
                }, fontSize = 12.sp
            )

            /* Checkbox. */

            Checkbox(
                checked = runner.checked,
                onCheckedChange = { checked ->
                    onCheckedChange(checked)
                },
                Modifier.constrainAs(idCb) {
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                },
                enabled = true
            )



        }
    }
}