package com.mcssoft.racedaycompose.ui.races

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.MeetingHeader
import com.mcssoft.racedaycompose.ui.components.navigation.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.races.components.RaceItem

@Composable
fun RacesScreen(
    navController: NavController,
    viewModel: RacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_races),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(ScreenRoute.MeetingsScreen.route) {
                        popUpTo(ScreenRoute.MeetingsScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(ScreenRoute.MeetingsScreen.route) {
                            popUpTo(ScreenRoute.MeetingsScreen.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_home_24),
                            stringResource(id = R.string.lbl_icon_home)
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            ) {
                state.meeting?.let { meeting ->
                    MeetingHeader(
                        meeting = meeting,
                        MaterialTheme.colors.background
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp)
            ) {
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
                )
            }
            if (state.loading) {
                LoadingDialog(
                    titleText = stringResource(id = R.string.dlg_loading_title),
                    msgText = stringResource(id = R.string.dlg_loading_msg)
                )
            }
        }
    }
}
