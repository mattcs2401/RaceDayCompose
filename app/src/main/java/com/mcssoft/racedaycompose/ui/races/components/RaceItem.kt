package com.mcssoft.racedaycompose.ui.races.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.ui.theme.*

@Composable
fun RaceItem(
    race: Race,
    onItemClick: (Race) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding4dp),
        shape = RoundedCornerShapes.medium,
        elevation = elevation4dp
        //backgroundColor = TBA
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { onItemClick(race) }
        ) {

            val (idRaceNo, idRaceName, idRaceTime, idRaceDist) = createRefs()

            Text(
                race.raceNumber.toString(),
                Modifier.constrainAs(idRaceNo) {
                    top.linkTo(parent.top, margin = margin16dp)
                    start.linkTo(parent.start, margin = margin16dp)
                }, fontSize = 12.sp
            )

            Text(
                race.raceName,
                Modifier.constrainAs(idRaceName) {
                    top.linkTo(idRaceNo.top, margin = margin0dp)
                    start.linkTo(idRaceNo.end, margin = margin8dp)
                }, fontSize = 12.sp
            )

            Text(
                race.raceTime,
                Modifier.constrainAs(idRaceTime) {
                    top.linkTo(idRaceName.bottom, margin = margin8dp)
                    start.linkTo(idRaceName.start, margin = margin0dp)
                    bottom.linkTo(parent.bottom, margin = margin16dp)
                }, fontSize = 12.sp
            )

            Text(
                "${race.distance}m",
                Modifier.constrainAs(idRaceDist) {
                    top.linkTo(idRaceTime.top, margin = margin0dp)
                    start.linkTo(idRaceTime.end, margin = margin16dp)
                }, fontSize = 12.sp
            )
        }
    }
}