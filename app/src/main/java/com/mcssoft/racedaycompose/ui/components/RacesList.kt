package com.mcssoft.racedaycompose.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.ui.races.components.RaceItem

@Composable
fun RacesList(
    races: List<Race>,
    onItemClick: (Any) -> Unit
) {
    ConstraintLayout {

        val (idLazyCol) = createRefs()

        LazyColumn(modifier = Modifier.constrainAs(idLazyCol) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }) {
            items(items = races) { race ->
                RaceItem(race = race, onItemClick = { })
            }
        }
    }
}