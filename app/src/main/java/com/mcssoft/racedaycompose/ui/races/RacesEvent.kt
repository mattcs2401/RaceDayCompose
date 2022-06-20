package com.mcssoft.racedaycompose.ui.races

sealed class RacesEvent {

    data class Retry(val mtgId: Long = 0): RacesEvent()

    object Cancel: RacesEvent()

}