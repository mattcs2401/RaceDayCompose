package com.mcssoft.racedaycompose.utility

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.dto.MeetingDto
import com.mcssoft.racedaycompose.domain.dto.RaceDto
import com.mcssoft.racedaycompose.domain.dto.toMeeting
import com.mcssoft.racedaycompose.domain.dto.toRace
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.Constants.NO_MEETINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val iDbRepo: IDbRepo
) {

    suspend fun getMeetings(mtgType: String = ""): DataResult<List<Meeting>> {
        var meetings = listOf<Meeting>()
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
        if(meetings.count() > 0) {
            return DataResult.Success(meetings)
        } else {
            return DataResult.Error(NO_MEETINGS)
        }
    }

//    suspend fun getRaces(mtgId: Long): Flow<DataResult<List<Race>>> = flow {
//        try {
//            var races = listOf<Race>()
//
//            emit(DataResult.Loading())
//
//            races = iDbRepo.getRaces(mtgId)
//
//            emit(DataResult.Success(races))
//
//        } catch(ex: Exception) {
//            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
//        }
//    }

    // TODO - get Runners.
}