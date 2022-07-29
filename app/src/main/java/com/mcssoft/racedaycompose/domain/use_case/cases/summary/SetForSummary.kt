package com.mcssoft.racedaycompose.domain.use_case.cases.summary

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner
import com.mcssoft.racedaycompose.domain.model.Summary
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Class to set a Summary object.
 */
class SetForSummary @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(raceId: Long, runnerId: Long, checked: Boolean): Flow<DataResult<String>> = flow {
        try {
            emit(DataResult.loading())

            val race = iDbRepo.getRace(raceId)
            val meeting = iDbRepo.getMeeting(race.mtgId)
            val runner = iDbRepo.getRunner(runnerId)

            if(checked) {
                val summary = createSummaryEntry(race, meeting, runner)
                iDbRepo.insertSummary(summary)
            } else {
                iDbRepo.deleteSummary(runnerId)
            }

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }
}

private fun createSummaryEntry(race: Race, meeting: Meeting, runner: Runner): Summary {
    return Summary().apply {
        mId = meeting._id
        rcId = race._id
        rrId = runner._id
        meetingCode = meeting.meetingCode
        meetingId = meeting.meetingId
        venueName = meeting.venueName
        raceName = race.raceName
        raceNumber = race.raceNumber
        raceTime = race.raceTime
        raceDist = race.distance
        riderName = runner.riderName
        runnerName = runner.runnerName
        runnerNumber = runner.runnerNumber
    }
}
