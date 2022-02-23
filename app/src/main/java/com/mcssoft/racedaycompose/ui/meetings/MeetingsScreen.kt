package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel

@Composable()
fun MeetingListScreen(navController: NavController,
                        viewModel: MeetingsViewModel = hiltViewModel()) {

    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(state.coins) { coin ->
//                CoinListItem(
//                    coin = coin,
//                    onItemClick = {
//                        navController.navigate(ScreenRoute.CoinDetailScreen.route + "/${coin.id}")
//                    }
//                )
//            }
        }
        if(state.error.isNotBlank()) {
//            Text(
//                text = state.error,
//                color = MaterialTheme.colors.error,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 20.dp)
//                    .align(Alignment.Center)
//            )
        }
        if(state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}