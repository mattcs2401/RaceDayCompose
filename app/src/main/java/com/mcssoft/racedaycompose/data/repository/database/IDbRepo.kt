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
    /* Meeting related. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeeting(meeting: Meeting): Long

//    @Insert// TBA (onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertMeetings(meetings: List<Meeting>)

    @Query("select * from Meeting")
    suspend fun getMeetings(): List<Meeting>

    @Query("delete from meeting")
    suspend fun deleteMeetings()                // CASCADE should take care of Races/Runners etc.

    /* Race related. */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRaces(races: List<Race>): List<Long>

}