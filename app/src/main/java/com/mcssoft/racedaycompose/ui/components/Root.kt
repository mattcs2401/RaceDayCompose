package com.mcssoft.racedaycompose.ui.components

import android.view.Window
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
import com.mcssoft.racedaycompose.ui.races.RacesScreen
import com.mcssoft.racedaycompose.ui.splash.SplashScreen
import com.mcssoft.racedaycompose.ui.theme.RaceDayComposeTheme

@Composable
fun Root(window: Window) {
    RaceDayComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.SplashScreen.route
            ) {
                composable(route = ScreenRoute.SplashScreen.route) {
                    SplashScreen(navController = navController)
                }
                composable(
                    route = ScreenRoute.MeetingsScreen.route
                ) {
                    MeetingsScreen(
                        navController = navController,
                        backPressedDispatcher = OnBackPressedDispatcher()
                    )
                }
                composable(
                    route = ScreenRoute.RacesScreen.route + "meetingId={meetingId}",
                    arguments = listOf(navArgument("meetingId") {
                        type = NavType.LongType
                        defaultValue = 0
                    })
                ) {
                    val meetingId = it.arguments?.getLong("meetingId") ?: 0
                        RacesScreen(
                            navController = navController,
                            meetingId = meetingId
                        )
                }
            }
        }
    }
}