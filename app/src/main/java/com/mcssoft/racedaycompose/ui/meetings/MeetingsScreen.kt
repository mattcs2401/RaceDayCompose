package com.mcssoft.racedaycompose.ui.meetings

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.Loading
import com.mcssoft.racedaycompose.ui.components.RefreshDialog
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.mcssoft.racedaycompose.ui.theme.custom.spacing

@Composable
fun MeetingsScreen(navController: NavController,
                   viewModel: MeetingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val showRefreshDialog = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Meetings") },
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

        )}
        //, backgroundColor = Color.DarkGray
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.meetings
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
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium)
                        .align(Alignment.Center)
                )
            }
            if (state.loading) {
                Loading("Loading ...")
            }
            if(showRefreshDialog.value) {
                RefreshDialog(onConfirmClicked = {
                    showRefreshDialog.value = !showRefreshDialog.value

                    viewModel.onEvent(MeetingsEvent.Refresh())
                },
                onDismiss = {
                        showRefreshDialog.value = !showRefreshDialog.value
                })
            }
        }
    }

}