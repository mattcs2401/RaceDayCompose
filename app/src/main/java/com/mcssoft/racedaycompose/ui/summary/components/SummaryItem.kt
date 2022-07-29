package com.mcssoft.racedaycompose.ui.summary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.mcssoft.racedaycompose.domain.model.Summary
import com.mcssoft.racedaycompose.ui.theme.RoundedCornerShapes
import com.mcssoft.racedaycompose.ui.theme.elevation4dp
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
//            Text(
//                text = summary.meetingCode,
//                Modifier.layoutId("idMtgCode"),
//                //fontSize = fontSize12sp
//            )
//            Text(
//                text = summary.venueName,
//                Modifier.layoutId("idVenue")
//                //fontSize = fontSize12sp
//            )
            Text(
                text = "${summary.runnerNumber}: ${summary.runnerName}",
                Modifier.layoutId("idHorse")
                //fontSize = fontSize12sp
            )
            Text(
                text = "${summary.raceDist}m",
                Modifier.layoutId("idDist"),
                // fontSize = fontSize12sp
            )
//            Text(
//                text = "S: ${summary.runnerNumber}",
//                Modifier.layoutId("idSelNo")
//                //fontSize = fontSize12sp
//            )
//            Text(
//                text = summary.raceTime,
//                Modifier.layoutId("idTime")
//                //fontSize = fontSize12sp
//            )
        }
    }
}

private val constraintSet = ConstraintSet {
    // 1st line.
//    val idMtgCode = createRefFor("idMtgCode")
//    val idVenue = createRefFor("idVenue")
    val idHorse = createRefFor("idHorse")
    val idDist = createRefFor("idDist")


    val idRaceNo = createRefFor("idRaceNo")
    val idRaceName = createRefFor("idRaceName")
    val idSelNo = createRefFor("idSelNo")
    val idTime = createRefFor("idTime")
    // 2nd line.

    val idJockey = createRefFor("idJockey")

    // 1st line layout.
//    constrain(idMtgCode) {
//        top.linkTo(parent.top, margin = 8.dp)
//        start.linkTo(parent.start, margin = 16.dp)
//    }
//    constrain(idVenue) {
//        top.linkTo(idMtgCode.top, margin = 0.dp)
//        start.linkTo(idMtgCode.end, margin = 4.dp)
//    }
    constrain(idHorse) {
//        top.linkTo(idVenue.top, margin = 0.dp)
//        start.linkTo(idVenue.end, margin = 8.dp)
    }
    constrain(idDist) {
        top.linkTo(idHorse.top, margin = 0.dp)
        start.linkTo(idHorse.end, margin = 8.dp)
    }

//    constrain(idSelNo) {
//        top.linkTo(idRaceNo.top, margin = 0.dp)
//        start.linkTo(idRaceNo.end, margin = 8.dp)
//    }
//    constrain(idTime) {
//        top.linkTo(idSelNo.top, margin = 0.dp)
//        start.linkTo(idSelNo.end, margin = 8.dp)
//    }

}
