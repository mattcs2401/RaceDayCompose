package com.mcssoft.racedaycompose.utility

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.Constants.NO_MEETINGS
import com.mcssoft.racedaycompose.utility.Constants.NO_RACES
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val iDbRepo: IDbRepo
) {

    suspend fun getMeeting(mId: Long): DataResult<Meeting> {
        var meeting: Meeting
        try {
            meeting = iDbRepo.getMeeting(mId)

        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
            "DbUtils.getMeeting(): An unexpected error occurred.")
        }
        return DataResult.Success(meeting)
    }

    suspend fun getMeetings(mtgType: String = ""): DataResult<List<Meeting>> {
        val meetings: List<Meeting>
        try {
            meetings = if(mtgType == "") {
                iDbRepo.getMeetings()
            } else {
                iDbRepo.getMeetings(mtgType)
            }
        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
                "DbUtils.getMeetings(): An unexpected error occurred.")
        }
        return if(meetings.count() > 0) {
            DataResult.Success(meetings)
        } else {
            DataResult.Error(NO_MEETINGS)
        }
    }

    suspend fun getRaces(mtgId: Long): DataResult<List<Race>> {
        val races: List<Race>
        try {
            races = iDbRepo.getRaces(mtgId)

        } catch(ex: Exception) {
            return DataResult.Error(ex.localizedMessage ?:
            "DbUtils.getRaces(): An unexpected error occurred.")
        }
        return if(races.count() > 0) {
            DataResult.Success(races)
        } else {
            DataResult.Error(NO_RACES)
        }
    }

    // TODO - get Runners.
}