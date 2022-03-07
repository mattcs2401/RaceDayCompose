package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Cache adaptation of domain.dto.MeetingDto.
 */
@Entity(tableName = "Meeting",
        indices = [Index(value = ["_id"])])
data class Meeting(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,

    var meetingId: Int? = null,
    var abandoned: Boolean = false,     //
    var meetingCode: String = "",       //
    var meetingType: String = "",       //
    var venueName: String = ""         //
) {
    /* Derived after construction, from the 1st associated RaceDto. The details are not part of the
       MeetingDto object. Info is used as a type of "meeting summary" in the view.
    */
    var meetingTime: String = ""       // time of 1st Race, used as the "meeting time" in the view.
    var racesNo: Int = 0               // the number of Races in the Meeting.
    var weatherCond: String = ""       // e.g. Overcast.
    var trackCond: String = ""         // Good
    var trackRating: Int = 0           // e.g. 3
}
