package com.mcssoft.racedaycompose.ui.races

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.MeetingHeader
import com.mcssoft.racedaycompose.ui.components.dialog.CommonDialog
import com.mcssoft.racedaycompose.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.mcssoft.racedaycompose.ui.destinations.RunnersScreenDestination
import com.mcssoft.racedaycompose.ui.races.RacesState.Status.*
import com.mcssoft.racedaycompose.ui.races.components.RaceItem
import com.mcssoft.racedaycompose.ui.theme.height64dp
import com.mcssoft.racedaycompose.ui.theme.padding64dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
/**
 * @param navigator: The Navigation.
 * @param meetingId: Passed here but used by the view model (retrieved through the nav args).
 * @param viewModel: The associated view model.
 */
fun RacesScreen(
    navigator: DestinationsNavigator,
    meetingId: Long = 0,
    viewModel: RacesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_races),
                backgroundColour = MaterialTheme.colors.primary,
                backNavIcon = R.drawable.ic_arrow_back_24,
                onBackPressed = {
                    navigator.navigate(MeetingsScreenDestination) {
                        popUpTo(route = MeetingsScreenDestination) {
                            inclusive = true
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navigator.navigate(MeetingsScreenDestination) {
                            popUpTo(route = MeetingsScreenDestination) {
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
        // Meeting header.
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
                state.mtg?.let { meeting ->
                    MeetingHeader(
                        meeting = meeting,
                        MaterialTheme.colors.background
                    )
                }
            }
            // Races listing.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding64dp)
            ) {
                items(
                    items = state.races
                ) { race ->
                    RaceItem(
                        race = race,
                        onItemClick = {
                            navigator.navigate(RunnersScreenDestination(race._id))
                        }
                    )
                }
            }
            ManageState(
                racesState = state,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ManageState(
    racesState: RacesState,
    viewModel: RacesViewModel
) {
    val errorDialogShow = remember { mutableStateOf(false) }

    when (racesState.status) {
        is Initialise -> {}
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
                racesState.mtgId,
                viewModel = viewModel
            )
        }
        is Success -> {/* TBA */}
    }
}

@Composable
private fun ShowErrorDialog(
    showError: MutableState<Boolean>,
    mtgId: Long,
    viewModel: RacesViewModel
) {
    if (showError.value) {
        showError.value = !showError.value
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = "An Error Occurred",
            dialogText = "Unable to get the Races listing.",
            dismissButtonText = "Cancel",
            onDismissClicked = {
                viewModel.onEvent(RacesEvent.Cancel)
            },
            confirmButtonText = "Retry",
            onConfirmClicked = {
                viewModel.onEvent(RacesEvent.Retry(mtgId))
            }
        )
    }
}
