package com.mcssoft.racedaycompose.utility

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val iDbRepo: IDbRepo
) {

    suspend fun getMeeting(mtgId: Long): DataResult<Meeting> {
        val meeting: Meeting
        try {
            meeting = iDbRepo.getMeeting(mtgId)

        } catch(exception: Exception) {
            return DataResult.failure(exception)
        }
        return DataResult.success(meeting)
    }

    suspend fun getMeetings(): DataResult<List<Meeting>> {
        val meetings: List<Meeting>
        try {
            meetings = iDbRepo.getMeetings()
        } catch(exception: Exception) {
            return DataResult.failure(exception)
        }
        return DataResult.success(meetings)
    }

//    suspend fun getMeetingCodes(): DataResult<List<String>> {
//        val codes: List<String>
//        try {
//            codes = iDbRepo.getMeetingCodes()
//        } catch(exception: Exception) {
//            return DataResult.failure(exception)
//        }
//        return DataResult.success(codes)
//    }

    suspend fun getRaces(mtgId: Long): DataResult<List<Race>> {
        val races: List<Race>
        try {
            races = iDbRepo.getRaces(mtgId)

        } catch(exception: Exception) {
            return DataResult.failure(exception)
        }
        return DataResult.success(races)
    }

    suspend fun getRace(raceId: Long): DataResult<Race> {
        val race: Race
        try {
            race = iDbRepo.getRace(raceId)

        } catch(exception: Exception) {
            return DataResult.failure(exception)
        }
        return DataResult.success(race)
    }

    suspend fun getRunners(raceId: Long): DataResult<List<Runner>> {
        val runners: List<Runner>
        try {
            runners = iDbRepo.getRunners(raceId)

        } catch(exception: Exception) {
            return DataResult.failure(exception)
        }
        return DataResult.success(runners)
    }

}