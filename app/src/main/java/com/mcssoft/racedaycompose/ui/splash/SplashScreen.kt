package com.mcssoft.racedaycompose.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.mcssoft.racedaycompose.ui.components.navigation.Screen

/**
 * App starting point.
 *
 * ------
 * Notes:
 * ------
 * The Compose navigation doesn't like the startDestination to have any nav arguments, even
 * default values. This screen was implemented purely to allow for the MeetingsScreen (the actual
 * home/startDestination) to have nav arguments.
 */
@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(key1 = true) {
        navController.navigate(Screen.MeetingsScreen.route + "prefsChange=${false}")
    }
}