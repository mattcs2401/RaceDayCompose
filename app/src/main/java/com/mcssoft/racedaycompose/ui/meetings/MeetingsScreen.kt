package com.mcssoft.racedaycompose.ui.meetings

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.DefaultDialog
import com.mcssoft.racedaycompose.ui.components.Loading
import com.mcssoft.racedaycompose.ui.meetings.MeetingsState.Status.*
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun MeetingsScreen(
    context: Context,
    navController: NavController,
    viewModel: MeetingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val appState by viewModel.appState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    val showRefreshDialog = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.label_meetings)) },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    IconButton(onClick = {
                        showRefreshDialog.value = true
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
            when(state.status) {
                is Loading -> {
                    Loading(stringResource(id = R.string.label_loading))
                }
                is Failure -> {
                    Text(
                        text = state.exception?.localizedMessage ?: "An unknown error occurred.",
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium)
                            .align(Alignment.Center)
                    )
                }
                is Success -> {
                    if(appState.isRefreshing && appState.meetingsDownloaded) {
                        Log.d("TAG", "MeetingsState.successful - getting Runners.")
                        viewModel.setupRunnersFromApi(context)
                    }
                    Log.d("TAG", "MeetingsState.successful.")
                }
            }
            if(showRefreshDialog.value) {
                DefaultDialog(
                    dialogTitle =  stringResource(id = R.string.dlg_refresh_title),
                    dialogText = stringResource(id = R.string.dlg_refresh_text),
                    confirmButtonText = stringResource(id = R.string.lbl_btn_ok),
                    dismissButtonText = stringResource(id = R.string.lbl_btn_cancel),
                    onConfirmClicked = {
                        showRefreshDialog.value = !showRefreshDialog.value
                        // Trigger Refresh event through the ViewModel.
                        viewModel.onEvent(MeetingsEvent.Refresh())
                    },
                    onDismissClicked = {
                        showRefreshDialog.value = !showRefreshDialog.value
                    })
            }
        }
    }

}