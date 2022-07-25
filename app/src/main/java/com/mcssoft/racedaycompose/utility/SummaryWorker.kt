package com.mcssoft.racedaycompose.utility

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner
import com.mcssoft.racedaycompose.domain.model.Summary
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class SummaryWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoint {
        fun getDbRepo(): IDbRepo
    }

    private val entryPoint = EntryPointAccessors.fromApplication(context, IEntryPoint::class.java)
    val iDbRepo = entryPoint.getDbRepo()

    override suspend fun doWork(): Result {
        val raceId = inputData.getLong("key_race_id", -1)
        val runnerId = inputData.getLong("key_runner_id", -1)
        val checked = inputData.getBoolean("key_checked", false)

        try {
            val race = iDbRepo.getRace(raceId)
            val meeting = iDbRepo.getMeeting(race.mtgId)
            val runner = iDbRepo.getRunner(runnerId)

            if(checked) {
                val summary = createSummaryEntry(race, meeting, runner)
                iDbRepo.insertSummary(summary)
            } else {
                iDbRepo.deleteSummary(runnerId)
            }

        } catch (exception: Exception) {
            Log.d("TAG", "[SummaryWorker.doWork] exception.")
            val output = workDataOf("key_exception" to exception.localizedMessage)
            return Result.failure(output)
        }
        Log.d("TAG", "[SummaryWorker.doWork] ending.")
        return Result.success()
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
            riderName = runner.riderName
            runnerName = runner.runnerName
            runnerNumber = runner.runnerNumber
        }
    }



}
/*
    Logic:
    ------
    -> Check if a Summary has the passed in 'runnerId'
       -> No
            -> Then create a new Summary.
       -> Yes
            -> Is the passed in checked state == false ?
               -> Yes
                  -> Remove Summary
 */