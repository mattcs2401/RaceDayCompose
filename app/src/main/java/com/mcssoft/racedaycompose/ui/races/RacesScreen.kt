package com.mcssoft.racedaycompose.ui.races

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.RaceDayApp
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.Loading
import com.mcssoft.racedaycompose.ui.components.MeetingHeader
import com.mcssoft.racedaycompose.ui.components.RacesList
import com.mcssoft.racedaycompose.ui.components.TopBar
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.mcssoft.racedaycompose.ui.races.components.RaceItem

@Composable
fun RacesScreen(navController: NavController,
                viewModel: RacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
             TopBar(
                 title = stringResource(id = R.string.label_races),
                 MaterialTheme.colors.primary,
                 onBackPressed = {
                     navController.navigate(ScreenRoute.MeetingsScreen.route) {
                         popUpTo(ScreenRoute.MeetingsScreen.route) {
                             inclusive = true
                         }
                     }
                 },
                 Icons.Filled.ArrowBack
             )
        },
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
            ) {
                state.meeting?.let { meeting ->
                    MeetingHeader(meeting = meeting, MaterialTheme.colors.background) }
            }

            LazyColumn(modifier = Modifier.fillMaxSize() .padding(top = 64.dp)) {
                items(
                    items = state.races
                ) { race ->
                    RaceItem(
                        race = race,
                        onItemClick = {
                            navController.navigate(
                                ScreenRoute.RunnersScreen.route + "raceId=${race._id}"
                            )
                        }
                    )
                }
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center)
                )}
            if (state.loading) {
                Loading(stringResource(id = R.string.label_loading))
            }
        }
    }
}
