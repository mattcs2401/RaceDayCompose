package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cache equivalent to domain.Runner.
 */
@Entity
data class Runner(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val raceId: Long,
    val barrier: Int = 0,
    val drawDetails: String = "",
    val form: String = "",
    val handicap: Int = 0,
    val lastThreeStarts: String = "",
    val lateScratching: Boolean = false,
    val rating: Int = 0,
    val riderChanged: Boolean = false,
    val riderName: String = "",
    val runnerName: String = "",
    val runnerNumber: Int = 0,
    val scratched: Boolean = false,
    val silkImgUrl: String = "",
    val weight: Double = 0.0
)
/*
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
    var raceId: Long = 0,    // "foreign" key.

    val Barrier: Int,
    val DrawDetails: String?,
    val Form: String?,
    val Handicap: Int,
    val LastThreeStarts: String?,
    val LateScratching: Boolean,
    val Rating: Int,
    val RiderChanged: Boolean,
    val RiderName: String?,
    val RunnerName: String?,
    val RunnerNumber: Int,
    val Scratched: Boolean,
    val SilkImgUrl: String?,
    val Weight: Double,
 */