package com.mcssoft.racedaycompose.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Horse",
    foreignKeys = [
        ForeignKey(entity = Trainer::class, parentColumns = ["_id"], childColumns = ["tId"])
    ]
)
data class Horse(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    @ColumnInfo(index = true)
    var tId : Long = 0L,          // trainer id, foreign key.

    var shortName: String = "",   // e.g.
    var longName: String = "",    // e.g.
)