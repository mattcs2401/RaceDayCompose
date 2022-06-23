package com.mcssoft.racedaycompose.ui.runners

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.dialog.CommonDialog
import com.mcssoft.racedaycompose.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.mcssoft.racedaycompose.ui.destinations.RacesScreenDestination
import com.mcssoft.racedaycompose.ui.races.components.RaceHeader
import com.mcssoft.racedaycompose.ui.runners.RunnersState.Status.*
import com.mcssoft.racedaycompose.ui.runners.components.RunnerItem
import com.mcssoft.racedaycompose.ui.theme.height64dp
import com.mcssoft.racedaycompose.ui.theme.padding64dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
/**
 * @param navigator: DestinationsNavigator,
 * @param raceId: Passed here but used by the view model (retrieved through the nav args).
 * @param viewModel: The associated view model.
 */
fun RunnersScreen(
    navigator: DestinationsNavigator,
    raceId: Long = 0,
    viewModel: RunnersViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_runners),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navigator.navigate(RacesScreenDestination.route) {
                        popUpTo(route = RacesScreenDestination.route) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(MeetingsScreenDestination.route) {
                            popUpTo(route = MeetingsScreenDestination.route) {
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
        // Race details header.
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
            // Runners listing.
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
                            viewModel.onEvent(RunnersEvent.CheckRunner(runner._id, checked))
                        }
                    )
                }
            }
            ManageState(
                runnersState = state,
                viewModel = viewModel
            )
        }

    }

}

@Composable
private fun ManageState(
    runnersState: RunnersState,
    viewModel: RunnersViewModel
) {
    val errorDialogShow = remember { mutableStateOf(false) }

    when (runnersState.status) {
        is Initialise -> {}
        is Success -> {}
        is Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_title),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Failure -> {
            errorDialogShow.value = true
            ShowErrorDialog(
                errorDialogShow,
                runnersState.rceId,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ShowErrorDialog(
    showError: MutableState<Boolean>,
    rceId: Long,
    viewModel: RunnersViewModel
) {
    if (showError.value) {
        showError.value = !showError.value
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = stringResource(id = R.string.dlg_error_title),
            dialogText = "Unable to get the Runners listing.",
            dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
            onDismissClicked = {
                viewModel.onEvent(RunnersEvent.Cancel)
            },
            confirmButtonText = stringResource(id = R.string.lbl_btn_retry),
            onConfirmClicked = {
                viewModel.onEvent(RunnersEvent.Retry(rceId))
            }
        )
    }
}