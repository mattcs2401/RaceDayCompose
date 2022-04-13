package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun RaceHeader(race: Race, colour: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colour)
            .border(width = 2.dp, color = Color.Blue)
    )
    {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (idRaceNo,
                idRaceName,
                idRaceTime,
                idRaceDist) = createRefs()

            /* Top line. */

            Text(text = "R${race.raceNumber.toString()}",
                Modifier.constrainAs(idRaceNo) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                }, fontSize = 12.sp, fontWeight = FontWeight.Bold
            )

            Text(text = race.raceName,
                Modifier.constrainAs(idRaceName) {
                    top.linkTo(idRaceNo.top, margin = 0.dp)
                    start.linkTo(idRaceNo.end, margin = 16.dp)
                }, fontSize = 12.sp//, fontWeight = FontWeight.Bold
            )

            /* Second line. */

            Text(text = race.raceTime,
                Modifier.constrainAs(idRaceTime) {
                    top.linkTo(idRaceName.bottom, margin = 8.dp)
                    start.linkTo(idRaceName.start, margin = 0.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                }, fontSize = 12.sp
            )

            Text(text = "${race.distance}m",
                Modifier.constrainAs(idRaceDist) {
                    start.linkTo(idRaceTime.end, margin = 16.dp)
                    top.linkTo(idRaceTime.top, margin = 0.dp)
                }, fontSize = 12.sp
            )

        }
    }
}