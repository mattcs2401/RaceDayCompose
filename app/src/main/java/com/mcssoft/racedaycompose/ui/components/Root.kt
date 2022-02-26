package com.mcssoft.racedaycompose.ui.components

import android.view.Window
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
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
                // TODO - Races screen
            }
        }
    }
}