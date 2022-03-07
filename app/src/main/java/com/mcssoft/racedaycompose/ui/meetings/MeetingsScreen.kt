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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.Loading
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem

@Composable
fun MeetingsScreen(navController: NavController,
                   viewModel: MeetingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Meetings") },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                IconButton(onClick = {
                    viewModel.onEvent(MeetingsEvent.Refresh())
                })
                {
                    Icon(Icons.Default.Refresh, "Refresh")
                }
            }
        )},
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier.fillMaxSize().background(Color.Gray)) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.meetings
                ) { meeting ->
                    MeetingItem(
                        meeting = meeting,
                        onItemClick = {
                            navController.navigate(ScreenRoute.RacesScreen.route +
                                    "meetingId=${meeting._id}")
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
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
            if (state.isLoading) {
                Loading("Loading ...")
            }
        }
    }

}