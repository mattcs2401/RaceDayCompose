package com.mcssoft.racedaycompose.ui.races

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.TopBar
import com.mcssoft.racedaycompose.ui.meetings.components.MeetingItem
import com.mcssoft.racedaycompose.ui.races.components.RaceItem

@Composable
fun RacesScreen(navController: NavController,
                meetingId: Long,
                viewModel: RacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    viewModel.state.value.mId = meetingId
    viewModel.onEvent(RacesEvent.GetRaces())

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
             TopBar("Races", MaterialTheme.colors.primary, navController, Icons.Default.ArrowBackIos)
        },
        backgroundColor = Color.LightGray
    ) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.secondary)) {

//            Toast.makeText(LocalContext.current, "Meeting id: $meetingId", Toast.LENGTH_SHORT).show()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = state.races) { race ->
                    RaceItem(
                        race = race,
                        onItemClick = {
                            // TBA>
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
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
/*
//                actions = {
//                    IconButton(onClick = { /*viewModel.onEvent(MeetingsEvent.Refresh())*/ }) {
//                        Icon(Icons.Default.ArrowBack, "Refresh")
//                    }

 */