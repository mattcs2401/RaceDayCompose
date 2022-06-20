package com.mcssoft.racedaycompose.ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mcssoft.racedaycompose.ui.destinations.MeetingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
//@RootNavGraph(start = true)
@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {

    LaunchedEffect(key1 = true) {
        navigator.navigate(MeetingsScreenDestination)
    }
}