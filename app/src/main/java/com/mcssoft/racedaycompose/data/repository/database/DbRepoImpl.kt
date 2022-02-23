package com.mcssoft.racedaycompose.data.repository.database

import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbRepoImpl @Inject constructor(
    private val dao: IDbRepo
) : IDbRepo {

    override suspend fun insertMeeting(meeting: Meeting): Long {
        return dao.insertMeeting(meeting)
    }

//    override suspend fun insertMeetings(meetings: List<Meeting>) {
//        dao.insertMeetings(meetings)
//    }

    override fun getMeetings(): Flow<List<Meeting>> {
        return dao.getMeetings()
    }

    override suspend fun deleteMeetings() {
        return dao.deleteMeetings()
    }

    override suspend fun insertRaces(races: List<Race>): List<Long> {
        return dao.insertRaces(races)
    }
}