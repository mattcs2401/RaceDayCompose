package com.mcssoft.racedaycompose.utility

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.delay

class RunnersWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoints {
        fun getRemoteRepo(): IRemoteRepo
        fun getDbRepo(): IDbRepo
    }

    private val entryPoints = EntryPointAccessors.fromApplication(context, IEntryPoints::class.java)

    override suspend fun doWork(): Result {
        //val date = inputData.getString("key_date")
        //val bp = date
        Log.d("TAG", "[RunnerWorker.doWork] starting")

        delay(5000)

        val example = entryPoints.getDbRepo()

        Log.d("TAG", "[RunnerWorker.doWork] ending")

        return Result.success()
    }
}
/*
    /**
     * Save the Runner info to the database.
     * @param date: Used for the get from Api.
     * @return A Flow of empty string on success, else the exception.
     */
    operator fun invoke (date: String): Flow<DataResult<Any>> = flow {
        Log.d("TAG","[SetupRunnersFromApi.invoke()]")
        // Get the Meeting codes (used for Api call).
        val codes = iDbRepo.getMeetingCodes()
        delay(50) // TBA ?

        // Loop through the list of Meeting codes.
        codes.forEach { code ->
            Log.d("TAG","[SetupRunnersFromApi.invoke] $code")
            // Get from the Api.
            val baseDto = iRemoteRepo.getRaceDay(date, code)

            // Get the MeetingDto, there'll only be one but a list is returned.
            val meetingDto = baseDto.body.RaceDay.Meetings[0]
            Log.d("TAG","[SetupRunnersFromApi.invoke] ${meetingDto.toString()}")

            // Process the list of Races associated with the Meeting.
            val value = parseRaces(meetingDto)
            value.onEach { result ->
                when {
                    result.failed -> {
                        Log.d("TAG","[SetupRunnersFromApi.invoke.parseRaces()] result.failed")
                        emit(DataResult.failure(result.exception!!))
                    }
                    result.successful -> {
                        emit(DataResult.success(""))
                    }
                }
            }
        }
    }

    /**
     * Get the list of Races in preparation for process the associated Runners.
     * @param meetingDto: The MeetingDto object associated with the RaceDto.
     * @return A Flow of empty string on success, else the exception.
     */
    private suspend fun parseRaces(meetingDto: MeetingDto): Flow<DataResult<Any>> = flow {
        Log.d("TAG","[SetupRunnersFromApi.parseRaces]")
        try {
            // Get the database row id of the equivalent Meeting in the database.
            val mId = iDbRepo.getMeetingId(meetingDto.MeetingId)
            Log.d("TAG","[SetupRunnersFromApi.parseRaces()] mId:$mId")
            delay(50)  // TBA ?

            // Loop through the list of RaceDto.
            meetingDto.Races.forEach { raceDto ->
                // Get the (database row) of the Race.
                val rId = iDbRepo.getRaceId(mId, raceDto.RaceNumber)
                delay(50)  // TBA ?

                // Process for the list of associated RunnerDto.
                parseRunners(rId, raceDto).onEach { result ->
                    when {
                        result.failed -> {
                            emit(DataResult.failure(result.exception!!))
                        }
                        result.successful -> {
                            emit(DataResult.success(""))
                        }
                    }
                }
            }
            emit(DataResult.success(""))
        } catch(exception: Exception) {
            if(exception.localizedMessage!!.isNotBlank()) {
                emit(DataResult.failure(exception))
            } else {
                emit(DataResult.failure(
                    Exception("[SetupRunnersFromApi.parseRaces] Unknown exception or error."))
                )
            }

            Log.e("TAG [SetupRunnersFromApi.parseRaces]",
                exception.localizedMessage ?:
                     "[SetupRunnersFromApi.parseRaces] Unknown exception or error.")
        }
    }

    /**
     * Create the list of (domain) Runners and write the database.
     * @param rId: The (database row) id of the associated Race.
     * @param raceDto: The RaceDto object.
     * @return A Flow of empty string on success, else the exception.
     */
    private suspend fun parseRunners(rId: Long, raceDto: RaceDto): Flow<DataResult<Any>> = flow {
        Log.d("TAG","[SetupRunnersFromApi.parseRunners]")
        try {
            // Temporary list (used for database insert).
            val runners = arrayListOf<Runner>()
            // Create the (domain) Runners and add to listing.
            raceDto.Runners.forEach { runnerDto ->
                val runner = runnerDto.toRunner(rId)
                runners.add(runner)
            }
            // Insert the list of Runners.
            iDbRepo.insertRunners(runners)
            delay(50)  // TBA ?

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            if(exception.localizedMessage!!.isNotBlank()) {
                emit(DataResult.failure(exception))
            } else {
                emit(DataResult.failure(
                    Exception("[SetupRunnersFromApi.parseRunners] Unknown exception or error."))
                )
            }

            Log.e(
                "TAG [SetupRunnersFromApi.parseRunners]",
                exception.localizedMessage ?:
                "[SetupRunnersFromApi.parseRaces] Unknown exception or error.")
        }
    }

 */