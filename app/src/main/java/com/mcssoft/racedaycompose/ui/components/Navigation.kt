package com.mcssoft.racedaycompose.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mcssoft.racedaycompose.ui.ScreenRoute
import com.mcssoft.racedaycompose.ui.meetings.MeetingsScreen
import com.mcssoft.racedaycompose.ui.races.RacesScreen
import com.mcssoft.racedaycompose.ui.runners.RunnersScreen
import com.mcssoft.racedaycompose.ui.settings.SettingsScreen
import com.mcssoft.racedaycompose.ui.splash.SplashScreen

@Composable
fun Navigation( /* Note: Parameters simply passed through to MeetingScreen. */
    context: Context,
    owner: LifecycleOwner
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoute.SplashScreen.route) {
        composable(
            route = ScreenRoute.SplashScreen.route
        ){
            // "Dummy" starting point.
            SplashScreen(navController = navController)
        }
        composable(
            route = ScreenRoute.MeetingsScreen.route
        ) {
            MeetingsScreen(
                context = context,
                owner = owner,
                navController = navController
            )
        }
        composable(
            /* See notes below. */
            route = ScreenRoute.RacesScreen.route + "meetingId={meetingId}",
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
            route = ScreenRoute.RunnersScreen.route + "raceId={raceId}",
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
            route = ScreenRoute.SettingsScreen.route
        ) {
            SettingsScreen(
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