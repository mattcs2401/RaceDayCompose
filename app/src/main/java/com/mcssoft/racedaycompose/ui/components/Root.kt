package com.mcssoft.racedaycompose.ui.components

import android.view.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.meetings.MeetingListScreen
import com.mcssoft.racedaycompose.ui.theme.RaceDayComposeTheme

@Composable
fun Root(window: Window) {
    RaceDayComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.MeetingListScreen.route
            ) {
                composable(route = ScreenRoute.MeetingListScreen.route) {
                    MeetingListScreen(navController)
                }
//                        composable(
//                            route = ScreenRoute.CoinDetailScreen.route + "/{coinId}"
//                        ) {
//                            CoinDetailScreen()
//                        }
            }
        }
    }
}