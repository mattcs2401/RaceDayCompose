package com.mcssoft.racedaycompose.data.repository.database

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: IDbRepo
) : IDbRepo {

    //<editor-fold default state="collapsed" desc="Region: Meeting related.">
    override suspend fun insertMeeting(meeting: Meeting): Long {
        return dao.insertMeeting(meeting)
    }

    override suspend fun getMeetings(): List<Meeting> {
        return dao.getMeetings()
    }

    override suspend fun deleteMeetings() {
        return dao.deleteMeetings()
    }

    override suspend fun meetingCount(): Int {
        return dao.meetingCount()
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: Race related.">
    override suspend fun insertRaces(races: List<Race>): List<Long> {
        return dao.insertRaces(races)
    }

    //</editor-fold>
}