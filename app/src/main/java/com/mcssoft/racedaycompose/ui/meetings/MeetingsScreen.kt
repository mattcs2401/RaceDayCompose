package com.mcssoft.racedaycompose.ui.meetings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.AppState
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.Toast
import com.mcssoft.racedaycompose.ui.components.dialog.CommonDialog
import com.mcssoft.racedaycompose.ui.components.navigation.TopBar
import com.mcssoft.racedaycompose.ui.destinations.RacesScreenDestination
import com.mcssoft.racedaycompose.ui.destinations.SettingsScreenDestination
import com.mcssoft.racedaycompose.ui.destinations.SummaryScreenDestination
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState.Status.*
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun MeetingsScreen(
    navigator: DestinationsNavigator,
    viewModel: MeetingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val appState by viewModel.appState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val showRefresh = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = stringResource(id = R.string.label_meetings),
                backgroundColour = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        showRefresh.value = true
                    }) {
                        Icon(
                            //Icons.Default.Refresh,
                            painterResource(id = R.drawable.ic_refresh_24),
                            stringResource(id = R.string.lbl_icon_refresh)
                        )
                    }
                    IconButton(onClick = {
//                        navController.navigate(Screen.SettingsScreen.route)
                        navigator.navigate(SettingsScreenDestination)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_settings_24),
                            stringResource(id = R.string.lbl_icon_settings)
                        )
                    }
                    // TODO - Only display if Summary items exist.
                    if(viewModel.summaryCheck()) {
                        IconButton(onClick = {
//                            navController.navigate(Screen.SummaryScreen.route)
                            navigator.navigate(SummaryScreenDestination)
                        }) {
                            Icon(
                                painterResource(id = R.drawable.ic_summary_24),
                                stringResource(id = R.string.lbl_icon_summary)
                            )
                        }
                    }
                }
            )
        }
//        bottomBar = {
//            BottomBar(navController = navController)
//        }
    
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
                appState = appState,
                viewModel = viewModel,
                showRefresh = showRefresh
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
    appState: AppState,
    viewModel: MeetingsViewModel,
    showRefresh: MutableState<Boolean>
) {
    val errorDialogShow = remember { mutableStateOf(false) }

    if (showRefresh.value) {
        ShowRefreshDialog(show = showRefresh, viewModel = viewModel)
    }
    when (mtgsState.status) {
        is Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_title),
                msgText = stringResource(id = R.string.dlg_loading_msg),
                onDismiss = {}
            )
        }
        is Failure -> {
            showRefresh.value = false
            errorDialogShow.value = true
            ShowErrorDialog(
                errorDialogShow,
                viewModel = viewModel
            )
        }
        is Success -> {
            if (appState.isRefreshing && appState.meetingsDownloaded) {
                Toast("Getting Runners from the Api.")     // using Toast instead of SnackBar.
                // Get the Runner info from the Api.
                viewModel.setupRunnersFromApi(LocalContext.current)
            }
            if (!appState.isRefreshing && appState.runnersDownloaded) {
                Toast("Setup Runners from Api succeeded.")
            }
        }
    }
}

@Composable
private fun ShowRefreshDialog(
    show: MutableState<Boolean>,
    viewModel: MeetingsViewModel
) {
    CommonDialog(
        icon = R.drawable.ic_refresh_48,
        dialogTitle = stringResource(id = R.string.dlg_refresh_title),
        dialogText = stringResource(id = R.string.dlg_refresh_text),
        confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
        dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
        onConfirmClicked = {
            show.value = !show.value
            // Trigger Refresh event through the ViewModel.
            viewModel.onEvent(MeetingsEvent.Refresh())
        },
        onDismissClicked = {
            show.value = !show.value
        }
    )
}

@Composable
private fun ShowErrorDialog(
    showError: MutableState<Boolean>,
    viewModel: MeetingsViewModel
) {
    if (showError.value) {
        CommonDialog(
            icon = R.drawable.ic_error_48,
            dialogTitle = "No Connectivity",
            dialogText = "Check the internet connection or mobile data status.",
            dismissButtonText = "Refresh From Local",
            onDismissClicked = {
                showError.value = !showError.value
                viewModel.onEvent(MeetingsEvent.RefreshFromDb)
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
