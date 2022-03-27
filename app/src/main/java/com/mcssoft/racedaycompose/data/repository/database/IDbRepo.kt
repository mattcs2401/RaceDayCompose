package com.mcssoft.racedaycompose.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner

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
     * Get a Meeting based on it's row id.
     * @param mId: The Meeting id.
     * @return A Meeting.
     */
    @Query("select * from Meeting where _Id = :mId")
    suspend fun getMeeting(mId: Long): Meeting

    /**
     * Get a Meeting's id (record's row id) by the MeetingId value.
     * @param mId: The Meeting's MeetingId.
     * @return A Meeting.
     */
    @Query("select _id from Meeting where meetingId = :mId")
    suspend fun getMeetingId(mId: Int): Long


    @Query("select meetingCode from Meeting")
    suspend fun getMeetingCodes(): List<String>

    /**
     * Select all from Meetings.
     * @return A list of Meeting.
     */
//    @Query("select * from Meeting where meetingCode= :code")
//    suspend fun getMeetings(code: String): List<Meeting>

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

    /**
     * Get a listing of the Races based on their (foreign key) Meeting id.
     * @param mtgId: The Meeting id (database row number).
     * @return A list of Races.
     */
    @Query("select * from race where mtgId= :mtgId")
    suspend fun getRaces(mtgId: Long): List<Race>

    @Query("select * from Race where _id= :rId")
    suspend fun getRace(rId: Long): Race

    /**
     * Get the id (database row id) of a Race based on the (foreign key) Meeting id and Race number.
     * @param mtgId: The Meeting id (database row number).
     * @param raceNo: The Race number.
     * @return A the Race id.
     */
    @Query("select _id from Race where mtgId= :mtgId and raceNumber= :raceNo")
    suspend fun getRaceId(mtgId: Long, raceNo: Int): Long
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Runner related.">
    /**
     * Insert a list of Runners.
     * @param runners: The list of Runners.
     * @return A list of the _ids of the inserted Runner records. Usage is TBA ATT.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRunners(runners: List<Runner>): List<Long>

    /**
     * Get a list of Runners based on the id of the associated Race.
     * @param raceId: The Race id (database row id).
     * @return A list of Runners.
     */
    @Query("select * from Runner where raceId= :raceId")
    suspend fun getRunners(raceId: Long): List<Runner>
    //</editor-fold>
}