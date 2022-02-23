package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Cache adaptation of domain.dto.MeetingDto.
 */
@Entity(tableName = "Meeting",
        indices = [Index(value = ["_id"])])
data class Meeting(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var mtgId: Long = 0,
    var meetingId: Int? = null,
    var abandoned: Boolean = false,     //
    var meetingCode: String = "",       //
    var meetingType: String = "",       //
    var venueName: String = ""         //
) {
//    // Adapter flag, if true, the view in the adapter is in an expanded view.
//    var isExpanded: Boolean = false
}
