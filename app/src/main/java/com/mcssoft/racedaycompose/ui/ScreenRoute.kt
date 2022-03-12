package com.mcssoft.racedaycompose.ui

sealed class ScreenRoute(val route: String) {

    object MeetingsScreen: ScreenRoute("meetings_screen")

    object RacesScreen: ScreenRoute("races_screen")

    object RunnersScreen: ScreenRoute("runners_screen")    // TBA.

    object SummaryScreen: ScreenRoute("summary_screen")    // TBA.

    object SettingsScreen: ScreenRoute("settings_screen")
}
