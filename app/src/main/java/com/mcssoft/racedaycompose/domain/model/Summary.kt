package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Summary",
    indices = [
        Index(value = ["mId"]),
        Index(value = ["rcId"]),
        Index(value = ["rrId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Meeting::class,
            parentColumns = ["_id"],
            childColumns = ["mId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Race::class,
            parentColumns = ["_id"],
            childColumns = ["rcId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Runner::class,
            parentColumns = ["_id"],
            childColumns = ["rrId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Summary(

    // TODO - All values are TBA. Indexes appropriate ?

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var mId: Long = 0L,      // foreign key Meeting_id.
    var rcId: Long = 0L,     // foreign key Race_id.
    var rrId: Long = 0L,     // foreign key Runner_id.

    // Meeting related.
    var meetingId: Int? = null,
    var meetingCode: String = "",
    var venueName: String = "",

    // Race related.
    var raceName: String = "",
    var raceNumber: Int = 0,
    var raceTime: String = "",
    var raceDist: Int = 0,

    // Runner related.
    var riderName: String = "",
    var runnerName: String = "",
    var runnerNumber: Int = 0

)