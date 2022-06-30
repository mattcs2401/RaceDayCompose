package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Summary",
    foreignKeys = [
        ForeignKey(entity = Meeting::class, parentColumns = ["_id"], childColumns = ["mId"]),
        ForeignKey(entity = Race::class, parentColumns = ["_id"], childColumns = ["rId"]),
        ForeignKey(entity = Runner::class, parentColumns = ["_id"], childColumns = ["rrId"])],
    indices = [Index(value = ["_id", "mId", "rId", "rrId"])]
)
data class Summary(

    // TODO - All values are TBA.

    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var mId: Long = 0L,      // foreign key Meeting_id.
    var rId: Long = 0L,      // foreign key Race_id.
    var rrId: Long = 0L,     // foreign key Runner_id.

    // Meeting related.
    var meetingId: Int? = null,
    var meetingCode: String = "",
    var venueName: String = "",

    // Race related.
    var raceName: String = "",
    var raceNumber: Int = 0,
    var raceTime: String = "",

    // Runner related.
    val riderName: String = "",
    val runnerName: String = "",
    val runnerNumber: Int = 0

)