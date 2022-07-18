package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Trainer"
)
data class Trainer(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var shortName: String = "",   // e.g. T.Gollan
    var longName: String = "",    // e.g. Tony Gollan.
)