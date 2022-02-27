package com.mcssoft.racedaycompose.ui.meetings

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.components.HandleBackPressed
import com.mcssoft.racedaycompose.ui.components.MeetingItem
import com.mcssoft.racedaycompose.ui.components.MeetingItem2
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalMaterialApi::class)
@Composable()
fun MeetingsScreen(navController: NavController,
                   viewModel: MeetingsViewModel = hiltViewModel(),
                   backPressedDispatcher: OnBackPressedDispatcher
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    HandleBackPressed(backPressedDispatcher = backPressedDispatcher)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
            title = { Text("Meetings") },
            backgroundColor = MaterialTheme.colors.primary,
            actions = {
                IconButton(onClick = {
                    viewModel.onEvent(MeetingsEvent.Refresh())
                }
                ) {
                    Icon(Icons.Default.Refresh, "Refresh")
                }
            }
        )}
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = state.meetings
                ) { meeting ->
                    MeetingItem2(
                        meeting = meeting,
                        onItemClick = {
//                        navController.navigate(ScreenRoute.CoinDetailScreen.route + "/${coin.id}")
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
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}