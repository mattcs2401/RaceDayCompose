package com.mcssoft.racedaycompose.ui.runners

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.RaceHeader
import com.mcssoft.racedaycompose.ui.components.navigation.Screen
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.runners.components.RunnerItem
import com.mcssoft.racedaycompose.ui.theme.height64dp
import com.mcssoft.racedaycompose.ui.theme.padding16dp
import com.mcssoft.racedaycompose.ui.theme.padding64dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RunnersScreen(
    navController: NavController,
    viewModel: RunnersViewModel = hiltViewModel()
) {
    val state = viewModel.runnersState.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_runners),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navController.navigate(Screen.RacesScreen.route + "meetingId=${0}") {
                        popUpTo(Screen.RacesScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.MeetingsScreen.route + "prefsChange=${false}") {
                            popUpTo(Screen.MeetingsScreen.route) {
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
        },
        backgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.secondary)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height64dp)
            ) {
                state.race?.let { race ->
                    RaceHeader(race = race, MaterialTheme.colors.background)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding64dp)
            ) {
                items(
                    items = state.runners.filter { runner ->
                        !runner.lateScratching && !runner.scratched
                    }
                ) { runner ->
                    RunnerItem(
                        runner = runner,
                        onCheckedChange = { checked ->
                            viewModel.onEvent(runner._id, checked)
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
                        .padding(horizontal = padding16dp)
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
