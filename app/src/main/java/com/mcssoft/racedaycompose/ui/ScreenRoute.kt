package com.mcssoft.racedaycompose.ui

sealed class ScreenRoute(val route: String) {
    object MeetingListScreen: ScreenRoute("meeting_list_screen")
//    object CoinDetailScreen: ScreenRoute("coin_detail_screen")
}
