package com.mcssoft.racedaycompose.ui.components.navigation

sealed class ScreenRoute(val route: String) {

    object MeetingsScreen : ScreenRoute("meetings_screen")

    object RacesScreen : ScreenRoute("races_screen")

    object RunnersScreen : ScreenRoute("runners_screen")

    object SettingsScreen : ScreenRoute("settings_screen")

    object SummaryScreen : ScreenRoute("summary_screen")

    object SplashScreen : ScreenRoute("splash_screen")
}
