package com.mcssoft.racedaycompose.ui.races

sealed class RacesEvent {

    data class GetRaces(val mId: Long = 0): RacesEvent()
}