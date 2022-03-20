package com.mcssoft.racedaycompose.domain.dto

import com.mcssoft.racedaycompose.domain.model.Runner

data class RunnerDto(
    val Barrier: Int,
//    val Box: Int,
//    val DrawDetails: String,
//    val FPOfferId: Int,
//    val FPPlaceOdds: Double,
//    val FPPlaceOddsDeduction: Double,
//    val FPStatus: String,
//    val FPSuspended: Boolean,
//    val FPWinOdds: Double,
//    val FPWinOddsDeduction: Double,
    val Form: String,
//    val Handicap: Int,
//    val LastPlaceOdds: Double,
    val LastThreeStarts: String,
//    val LastWinOdds: Double,
    val LateScratching: Boolean,
//    val PlaceOdds: Double,
//    val PlaceOddsShortened: Boolean,
    val Rating: Int,
    val RiderChanged: Boolean,
    val RiderName: String,
    val RunnerName: String,
    val RunnerNumber: Int,
    val Scratched: Boolean,
//    val SilkImgUrl: String,
    val Weight: Double
//    val WinOdds: Double,
//    val WinOddsShortened: Boolean
)

fun RunnerDto.toRunner(rId: Long): Runner {
    return Runner(
        raceId = rId,
        barrier = Barrier,
        form = Form,
        lastThreeStarts = LastThreeStarts,
        lateScratching = LateScratching,
        rating = Rating,
        riderChanged = RiderChanged,
        riderName = RiderName,
        runnerName = RunnerName,
        runnerNumber = RunnerNumber,
        scratched = Scratched,
        weight = Weight
    )
}
