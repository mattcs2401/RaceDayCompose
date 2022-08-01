package com.mcssoft.racedaycompose.ui.summary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.mcssoft.racedaycompose.domain.model.Summary
import com.mcssoft.racedaycompose.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaycompose.ui.theme.elevation4dp
import com.mcssoft.racedaycompose.ui.theme.fontSize12sp
import com.mcssoft.racedaycompose.ui.theme.padding4dp

@Composable
fun SummaryItem(
    summary: Summary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp
        //backgroundColor = Col
    ) {
        ConstraintLayout(
            constraintSet,
            modifier = Modifier
                .clickable { }
        ) {
            // 1st line.
            Text(
                text = "R:${summary.raceNumber}",
                Modifier.layoutId("idRaceNo"),
                fontSize = fontSize12sp
            )
            Text(
                text = summary.raceName,
                Modifier.layoutId("idRaceName"),
                fontSize = fontSize12sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = summary.raceTime,
                Modifier.layoutId("idTime"),
                fontSize = fontSize12sp
            )
            // 2nd line.
            Text(
                text = "${summary.runnerNumber}",
                Modifier.layoutId("idRunnerNo"),
                fontSize = fontSize12sp
            )
            Text(
                text = summary.runnerName,
                Modifier.layoutId("idRunnerName"),
                fontSize = fontSize12sp
            )
            Text(
                text = summary.riderName,
                Modifier.layoutId("idRiderName"),
                fontSize = fontSize12sp
            )
            Text(
                text = "${summary.raceDist}m",
                Modifier.layoutId("idDist"),
                fontSize = fontSize12sp
            )
        }
    }
}

private val constraintSet = ConstraintSet {
    // 1st line.
    val idRaceNo = createRefFor("idRaceNo")
    val idRaceName = createRefFor("idRaceName")
    val idTime = createRefFor("idTime")

    // 2nd line.
    val idRunnerNo = createRefFor("idRunnerNo")
    val idRunnerName = createRefFor("idRunnerName")
    val idRiderName = createRefFor("idRiderName")
    val idDist = createRefFor("idDist")


    // 1st line layout.
    constrain(idRaceNo) {
        top.linkTo(parent.top, margin = 8.dp)
        start.linkTo(parent.start, margin = 8.dp)
    }
    constrain(idRaceName) {
        top.linkTo(idRaceNo.top, margin = 0.dp)
        start.linkTo(idRaceNo.end, margin = 8.dp)
    }
    constrain(idTime) {
        top.linkTo(idRaceNo.top, margin = 0.dp)
        end.linkTo(parent.end, margin = 8.dp)
    }
    // 2nd line.
    constrain(idRunnerNo) {
        top.linkTo(idRaceName.bottom, margin = 8.dp)
        start.linkTo(idRaceName.start, margin = 8.dp)
        bottom.linkTo(parent.bottom, margin = 8.dp)
    }
    constrain(idRunnerName) {
        top.linkTo(idRunnerNo.top, margin = 0.dp)
        start.linkTo(idRunnerNo.end, margin = 8.dp)
    }
    constrain(idRiderName) {
        top.linkTo(idRunnerName.top, margin = 0.dp)
        start.linkTo(idRunnerName.end, margin = 16.dp)
    }
    constrain(idDist) {
        top.linkTo(idRiderName.top, margin = 0.dp)
        end.linkTo(parent.end, margin = 8.dp)
    }

}
