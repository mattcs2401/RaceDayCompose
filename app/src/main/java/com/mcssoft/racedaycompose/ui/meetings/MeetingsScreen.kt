package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.components.dialog.CommonDialog
import com.mcssoft.racedaycompose.ui.components.dialog.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.navigation.BottomBar
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.RacesScreenDestination
import com.mcssoft.racedaycompose.ui.destinations.SplashScreenDestination
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState.Status.*
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MeetingsScreen(
    navigator: DestinationsNavigator,
    viewModel: MeetingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val showRefreshDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_meetings),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        showRefreshDialog.value = true
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navigator = navigator)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.body
                ) { meeting ->
                    MeetingItem(
                        meeting = meeting,
                        onItemClick = {
                            if (!meeting.abandoned) {
                                navigator.navigate(RacesScreenDestination(meeting._id))
                            }
                        }
                    )
                }
            }
            ManageState(
                mtgsState = state,
                showRefresh = showRefreshDialog,
                showError = showErrorDialog,
                navigator = navigator
            )
        }
    }

}

@Composable
/**
 * An attempt to group all the state related activity into one place.
 */
private fun ManageState(
    mtgsState: MeetingsState,
    showRefresh: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    navigator: DestinationsNavigator,
) {
    if (showRefresh.value) {
        ShowRefreshDialog(show = showRefresh, navigator = navigator)
    }
    when (mtgsState.status) {
        is Initialise -> {}
        is Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_title),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Failure -> {
            showRefresh.value = false
            showError.value = true
            ShowErrorDialog(show = showError, mtgsState.exception)
        }
        is Success -> {
            // TBA.
        }
    }
}

@Composable
private fun ShowRefreshDialog(
    show: MutableState<Boolean>,
    navigator: DestinationsNavigator,
) {
    CommonDialog(
        icon = R.drawable.ic_refresh_48,
        dialogTitle = stringResource(id = R.string.dlg_refresh_title),
        dialogText = stringResource(id = R.string.dlg_refresh_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            navigator.navigate(SplashScreenDestination)
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}

@Composable
private fun ShowErrorDialog(
    show: MutableState<Boolean>,
    exception: Exception?,  // message from any exception.
) {
    if (show.value) {
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = stringResource(id = R.string.dlg_error_title),
            dialogText = stringResource(id = R.string.dlg_error_msg_unknown),
            dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
            onDismissClicked = {
                show.value = !show.value
            },
            confirmButtonText = stringResource(id = R.string.lbl_btn_refresh),
            onConfirmClicked = {
                show.value = !show.value
                /** TODO - TBA what we do here ? **/
            }
        )
    }
}
/*
  SnackBar example:
  -----------------
1. val snackBarHostState = remember { SnackbarHostState() }
2. if (appState.isRefreshing && appState.meetingsDownloaded) {
                LaunchedEffect(key1 = true) {
                    snackBarHostState.showSnackbar(
                        message = "Getting Runners from the Api.",
                        actionLabel = "",
                        duration = SnackbarDuration.Short
                    )
                }
                SnackBar(snackBarHostState = snackBarHostState)
     Get the Runner info from the Api.
    viewModel.setupRunnersFromApi(LocalContext.current)
}
 */
