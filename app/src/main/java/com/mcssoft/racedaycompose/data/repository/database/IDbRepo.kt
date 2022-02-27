package com.mcssoft.racedaycompose.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import kotlinx.coroutines.flow.Flow

@Dao
interface IDbRepo {
    /*
      Note: All these must be called from a coroutine or suspending function.
    */

    //<editor-fold default state="collapsed" desc="Region: Meeting related.">
    /**
     * Insert a Meeting.
     * @param meeting: The Meeting to insert.
     * @return The _id of the inserted Meeting record.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: Meeting): Long

    /**
     * Select all from Meetings.
     * @return A list of Meeting.
     */
    @Query("select * from Meeting")
    suspend fun getMeetings(): List<Meeting>

    /**
     * Delete all from Meetings.
     */
    @Query("delete from meeting")
    suspend fun deleteMeetings()                // CASCADE should take care of Races/Runners etc.

    /**
     * Get a count of the Meeting records.
     */
    @Query("select count(*) from meeting")
    suspend fun meetingCount(): Int
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Race related.">
    /**
     * Insert a list of Races.
     * @param races: The list of Races.
     * @return A list of the _ids of the inserted Race records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>

    //</editor-fold>

}