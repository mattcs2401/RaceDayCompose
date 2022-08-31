package com.mcssoft.racedaycompose.ui.components.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaycompose.ui.admin.AdminScreen
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
import com.mcssoft.racedaycompose.ui.races.RacesScreen
import com.mcssoft.racedaycompose.ui.runners.RunnersScreen
import com.mcssoft.racedaycompose.ui.settings.SettingsScreen
import com.mcssoft.racedaycompose.ui.splash.SplashScreen
import com.mcssoft.racedaycompose.ui.summary.SummaryScreen

@Composable
fun NavGraph(
    context: Context    // TBA.
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController = navController)
        }
        composable(
            route = Screen.MeetingsScreen.route
        ) {
            MeetingsScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.RacesScreen.route + "meetingId={meetingId}",
            arguments = listOf(navArgument("meetingId") {
                type = NavType.LongType
            })
        ) {
            RacesScreen(navController = navController)
        }
        composable(
            route = Screen.RunnersScreen.route + "raceId={raceId}",
            arguments = listOf(navArgument("raceId") {
                type = NavType.LongType
            })
        ) {
            RunnersScreen(navController = navController)
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
        composable(
            route = Screen.AdminScreen.route
        ) {
            AdminScreen(
                navController = navController
            )
        }

    }

}
