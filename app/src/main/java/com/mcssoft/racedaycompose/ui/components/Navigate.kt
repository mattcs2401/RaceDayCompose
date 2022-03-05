package com.mcssoft.racedaycompose.ui.components

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
import com.mcssoft.racedaycompose.ui.races.RacesScreen

@Composable
fun Navigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.MeetingsScreen.route) {
        composable(route = ScreenRoute.MeetingsScreen.route) {
            MeetingsScreen(
                navController = navController
            )
        }
        composable(
            route = ScreenRoute.RacesScreen.route + "meetingId={meetingId}",
            arguments = listOf(navArgument("meetingId") {
                type = NavType.LongType
                defaultValue = 0
            })
        ) { entry ->
            val meetingId = entry.arguments?.getLong("meetingId") ?: 0
            RacesScreen(
                navController = navController,
                meetingId = meetingId
            )
        }
    }

}