package com.mcssoft.racedaycompose.ui.components.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
import com.mcssoft.racedaycompose.ui.races.RacesScreen
import com.mcssoft.racedaycompose.ui.runners.RunnersScreen
import com.mcssoft.racedaycompose.ui.settings.SettingsScreen
import com.mcssoft.racedaycompose.ui.splash.SplashScreen
import com.mcssoft.racedaycompose.ui.summary.SummaryScreen

@Composable
fun NavGraph(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.MeetingsScreen.route
        + "prefsChange={prefsChange}",
            arguments = listOf(navArgument(name = "prefsChange") {
                type = NavType.BoolType
                defaultValue = false
            })
        ) {
            MeetingsScreen(
                navController = navController
            )
        }
        composable(
            /* See notes below. */
            route = Screen.RacesScreen.route + "meetingId={meetingId}",
            arguments = listOf(navArgument("meetingId") {
                type = NavType.LongType
                defaultValue = -1
            })
        ) {
            RacesScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.RunnersScreen.route + "raceId={raceId}",
            arguments = listOf(navArgument("raceId") {
                type = NavType.LongType
                defaultValue = -1
            })
        ) {
            RunnersScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.SettingsScreen.route
        ) {
            SettingsScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.SummaryScreen.route
        ) {
            SummaryScreen(
                navController = navController
            )
        }
    }

}
/*
Notes:
- This creates a deeplink, don't have to explicitly pass the parameter to the RacesScreen.
- The SavedHandleState in the RacesViewModel constructor handles this.

From Logcat:
RacesViewModel.init()-savedStateHandle=[meetingId, android-support-nav:controller:deepLinkIntent]
*/