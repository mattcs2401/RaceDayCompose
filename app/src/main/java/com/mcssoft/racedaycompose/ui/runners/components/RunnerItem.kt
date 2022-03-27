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

//            val (idRaceNo, idRaceName, idRaceTime, idRaceDist) = createRefs()
//
//            Text(race.raceNumber.toString(),
//                Modifier.constrainAs(idRaceNo) {
//                    top.linkTo(parent.top, margin = 16.dp)
//                    start.linkTo(parent.start, margin = 16.dp)
//                }, fontSize = 12.sp
//            )
//
//            Text(race.raceName,
//                Modifier.constrainAs(idRaceName) {
//                    top.linkTo(idRaceNo.top, margin = 0.dp)
//                    start.linkTo(idRaceNo.end, margin = 8.dp)
//                }, fontSize = 12.sp
//            )
//
//            Text(DateUtils().getTime(race.raceTime),
//                Modifier.constrainAs(idRaceTime) {
//                    top.linkTo(idRaceName.bottom, margin = 8.dp)
//                    start.linkTo(idRaceName.start, margin = 0.dp)
//                    bottom.linkTo(parent.bottom, margin = 16.dp)
//                }, fontSize = 12.sp
//            )
//
//            Text("${race.distance}m",
//                Modifier.constrainAs(idRaceDist) {
//                    top.linkTo(idRaceTime.top, margin = 0.dp)
//                    start.linkTo(idRaceTime.end, margin = 16.dp)
//                }, fontSize = 12.sp
//            )



        }
    }
}