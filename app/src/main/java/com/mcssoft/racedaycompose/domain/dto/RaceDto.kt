package com.mcssoft.racedaycompose.domain.dto

import com.mcssoft.racedaycompose.domain.model.Race

data class RaceDto(
    val Distance: Int = 0,
//    val FeatureRaceBonusActive: String,
//    val FixedOdds: FixedOddsDto,
//    val Pools: List<PoolXDto>,
    val RaceName: String = "",
    val RaceNumber: Int = 0,
    val RaceTime: String = "",
//    val Results: List<ResultDto>,
    val Status: String = "",
//    val Tips: List<TipDto>,
//    val TrackChanged: Boolean,
    val TrackCondition: String = "",
//    val TrackConditionLevel: Int,
    val TrackRating: Int = 0,
//    val TrackRatingChanged: Boolean,
//    val WeatherChanged: Boolean,
    val WeatherCondition: String = ""
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
        trackCondition = TrackCondition,
        trackRating = TrackRating,
        weatherCondition = WeatherCondition
    )
}
