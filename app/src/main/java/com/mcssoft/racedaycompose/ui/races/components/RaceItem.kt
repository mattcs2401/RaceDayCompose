package com.mcssoft.racedaycompose.ui.races.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Race

@Composable
fun RaceItem(
    race: Race,
    onItemClick: (Race) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        //backgroundColor = Col
    ) {
        ConstraintLayout(
            modifier = Modifier
                .clickable { onItemClick(race) }
        ) {

            val (idRaceNo, idRaceName, idRaceTime, idRaceDist) = createRefs()

            Text(race.raceNumber.toString(), Modifier.constrainAs(idRaceNo) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            })

            Text(race.raceName, Modifier.constrainAs(idRaceName) {
                top.linkTo(idRaceNo.top, margin = 0.dp)
                start.linkTo(idRaceNo.end, margin = 8.dp)
            })

            Text(race.raceName, Modifier.constrainAs(idRaceTime) {
                top.linkTo(idRaceName.bottom, margin = 0.dp)
                start.linkTo(idRaceName.start, margin = 8.dp)
            })

            Text(race.raceName, Modifier.constrainAs(idRaceDist) {
                top.linkTo(idRaceTime.top, margin = 0.dp)
                start.linkTo(idRaceTime.end, margin = 8.dp)
            })



        }
    }
}