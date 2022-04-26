package com.mcssoft.racedaycompose.ui.meetings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.AppState
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.RefreshDialog
import com.mcssoft.racedaycompose.ui.components.LoadingDialog
import com.mcssoft.racedaycompose.ui.components.SnackBar
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState.Status.*
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.mcssoft.racedaycompose.ui.theme.custom.spacing
import com.mcssoft.racedaycompose.ui.theme.framework.RoundedCornerShapes

@ExperimentalMaterialApi
@Composable
fun MeetingsScreen(
    context: Context,
    navController: NavController,
    viewModel: MeetingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val appState by viewModel.appState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val showRefresh = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.label_meetings)) },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        showRefresh.value = true
                    }) {
                        Icon(Icons.Default.Refresh, "Refresh")
                    }
                    IconButton(onClick = {
                        navController.navigate(ScreenRoute.SettingsScreen.route)
                    }) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.body
                ) { meeting ->
                    MeetingItem(
                        meeting = meeting,
                        onItemClick = {
                            if (!meeting.abandoned) {
                                navController.navigate(
                                    ScreenRoute.RacesScreen.route + "meetingId=${meeting._id}"
                                )
                            }
                        }
                    )
                }
            }
            ManageState(
                state = state,
                appState = appState,
                viewModel = viewModel,
                context = context,
                showRefresh = showRefresh
            )
        }
    }

}

@Composable
private fun ManageState(
    state: MeetingsState,
    appState: AppState,
    viewModel: MeetingsViewModel,
    context: Context,
    showRefresh: MutableState<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if(showRefresh.value) {
        ShowRefreshDialog(show = showRefresh, viewModel = viewModel)
    }
    when(state.status) {
        is Loading -> {
            LoadingDialog(
                titleText = stringResource(id = R.string.dlg_loading_title),
                msgText = stringResource(id = R.string.dlg_loading_msg)
            )
        }
        is Failure -> {
            // TODO - get the AppState, what sort of failure ?
            //      - some sort of dedicated dialog ?
            Text(
                text = state.exception?.localizedMessage ?: stringResource(id = R.string.unknown_error),
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            )
        }
        is Success -> {
            if(appState.isRefreshing && appState.meetingsDownloaded) {
                LaunchedEffect(key1 = true) {
                    snackbarHostState.showSnackbar(
                        message = "Getting Runners from the Api.",
                        actionLabel = "",
                        duration = SnackbarDuration.Short
                    )
                }
                SnackBar(snackBarHostState = snackbarHostState)
                viewModel.setupRunnersFromApi(context)
            }
            if(!appState.isRefreshing && appState.runnersDownloaded) {
                LaunchedEffect(key1 = null) {
                    snackbarHostState.showSnackbar(
                        message = "Setup Runners from Api succeeded.",
                        actionLabel = "Close",
                        duration = SnackbarDuration.Short
                    )
                }
                SnackBar(snackBarHostState = snackbarHostState)
            }
        }
    }
}

@Composable
private fun ShowRefreshDialog(
    show: MutableState<Boolean>,
    viewModel: MeetingsViewModel
) {
    RefreshDialog(
        dialogTitle =  stringResource(id = R.string.dlg_refresh_title),
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
        },
        shape = RoundedCornerShapes.small
    )
}

