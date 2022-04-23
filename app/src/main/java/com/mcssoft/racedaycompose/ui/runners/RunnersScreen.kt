package com.mcssoft.racedaycompose.ui.runners

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
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.RaceHeader
import com.mcssoft.racedaycompose.ui.components.TopBar
import com.mcssoft.racedaycompose.ui.runners.components.RunnerItem

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
                MaterialTheme.colors.primary,
                onBackPressed = {
                    navController.navigate(ScreenRoute.RacesScreen.route + "meetingId=${0}") {
                        popUpTo(ScreenRoute.RacesScreen.route) {
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
                state.race?.let { race ->
                    RaceHeader(race = race, MaterialTheme.colors.background) }
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)) {
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
