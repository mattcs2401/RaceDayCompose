package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cache equivalent to domain.Runner.
 */
@Entity(
    tableName = "Runner",
    foreignKeys = [
        ForeignKey(
            entity = Race::class, parentColumns = ["_id"], childColumns = ["raceId"],
            onDelete = ForeignKey.CASCADE
        )],
    indices = [Index(value = ["raceId"])]
)
data class Runner(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0,

    val raceId: Long = 0,                   // foreign key.

    val barrier: Int = 0,
    val form: String = "",
    val lastThreeStarts: String = "",
    val lateScratching: Boolean = false,
    val rating: Int = 0,
    val riderChanged: Boolean = false,
    val riderName: String = "",
    val runnerName: String = "",
    val runnerNumber: Int = 0,
    val scratched: Boolean = false,
    val weight: Double = 0.0,
    var checked: Boolean = false,
    var trainer: String = ""
)
