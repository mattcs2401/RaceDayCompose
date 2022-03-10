package com.mcssoft.racedaycompose.utility

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val iDbRepo: IDbRepo
) {

    suspend fun getMeeting(mtgId: Long): DataResult<Meeting> {
        val meeting: Meeting
        try {
            meeting = iDbRepo.getMeeting(mtgId)

        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
            "DbUtils.getMeeting(): An unexpected error occurred.")
        }
        return DataResult.Success(meeting)
    }

    suspend fun getMeetings(): DataResult<List<Meeting>> {
        val meetings: List<Meeting>
        try {
            meetings = iDbRepo.getMeetings()
        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
                "DbUtils.getMeetings(): An unexpected error occurred.")
        }
        return DataResult.Success(meetings)
    }

    suspend fun getRaces(mtgId: Long): DataResult<List<Race>> {
        val races: List<Race>
        try {
            races = iDbRepo.getRaces(mtgId)

        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
            "DbUtils.getRaces(): An unexpected error occurred.")
        }
        return DataResult.Success(races)
    }

    // TODO - get Runners.
}