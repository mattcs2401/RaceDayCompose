package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cache equivalent to domain.RaceDto.
 */
@Entity(
    tableName = "Race",
    indices = [Index(value = ["mtgId"])],
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["_id"],
            childColumns = ["mtgId"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class Race(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var mtgId: Long = 0,     // "foreign" key.

    var distance: Int = 0,
    var raceName: String = "",
    var raceNumber: Int = 0,
    var raceTime: String = "",
    var status: String = "",
    var trackCondition: String = "",
    var trackRating: Int = 0,
    var weatherCondition: String = ""
)
