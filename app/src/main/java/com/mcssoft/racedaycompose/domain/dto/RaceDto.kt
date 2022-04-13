package com.mcssoft.racedaycompose.domain.dto

import android.text.format.DateUtils
import com.mcssoft.racedaycompose.domain.model.Race

data class RaceDto(
    val Distance: Int = 0,
    val RaceName: String = "",
    val RaceNumber: Int = 0,
    val RaceTime: String = "",
    val Status: String = "",
    val TrackCondition: String? = null,     // e.g. Good (but info can be missing).
    val TrackRating: Int = 0,               // e.g. 3
    val WeatherCondition: String? = null,   // e.g. Overcast (but info can be missing).
    val Runners: List<RunnerDto>

//    Not used ATT.
//    val FeatureRaceBonusActive: String,
//    val FixedOdds: FixedOddsDto,
//    val Pools: List<PoolDto>,
//    val Results: List<ResultDto>,
//    val Tips: List<TipDto>,
//    val TrackChanged: Boolean,
//    val TrackConditionLevel: Int,
//    val TrackRatingChanged: Boolean,
//    val WeatherChanged: Boolean,
//    val WeatherConditionLevel: Int
)

fun RaceDto.toRace(mId: Long): Race {
    return Race(
        mtgId = mId,
        distance = Distance,
        raceName = RaceName,
        raceNumber = RaceNumber,
        raceTime = RaceTime,
        status = Status,
        trackCondition = TrackCondition ?: "",
        trackRating = TrackRating,
        weatherCondition = WeatherCondition ?: ""
    )
}
