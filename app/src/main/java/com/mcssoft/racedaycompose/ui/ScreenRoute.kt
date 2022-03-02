package com.mcssoft.racedaycompose.ui

sealed class ScreenRoute(val route: String) {

    object SplashScreen: ScreenRoute("splash_screen")

    object MeetingsScreen: ScreenRoute("meetings_screen")

    object RacesScreen: ScreenRoute("races_screen")

    object RunnersScreen: ScreenRoute("runners_screen")    // TBA.

    object SummaryScreen: ScreenRoute("summary_screen")    // TBA.

}
