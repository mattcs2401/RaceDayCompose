package com.mcssoft.racedaycompose.ui.races

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.ui.ScreenRoute

@Composable
fun RacesScreen(navController: NavController,
                meetingId: Long,
                viewModel: RacesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = { Text("Races") },
                backgroundColor = MaterialTheme.colors.primary,
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(ScreenRoute.MeetingsScreen.route) }) {
                        Icon(Icons.Default.ArrowBackIos, "Back")
                    }
                }
            )
        },  // end topBar.
        backgroundColor = Color.LightGray
    ) {

    }
}
/*
//                actions = {
//                    IconButton(onClick = { /*viewModel.onEvent(MeetingsEvent.Refresh())*/ }) {
//                        Icon(Icons.Default.ArrowBack, "Refresh")
//                    }

 */