package com.mcssoft.racedaycompose.domain.use_case.cases

import android.util.Log
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.dto.MeetingDto
import com.mcssoft.racedaycompose.domain.dto.RaceDto
import com.mcssoft.racedaycompose.domain.dto.toRunner
import com.mcssoft.racedaycompose.domain.model.Runner
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SaveRunners @Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo
) {
    /*
      Notes:
      Can't seem to be able to use other use cases here ??
     */

    /**
     * Save the Runner info to the database.
     * @param date: Used for the get from Api.
     * @return A Flow of empty string on success, else the exception.
     */
    operator fun invoke (date: String): Flow<DataResult<Any>> = flow {
        // Get the Meeting codes (used for Api call).
        val codes = iDbRepo.getMeetingCodes()
        delay(50) // TBA ?

        // Loop through the list of Meeting codes.
        codes.forEach { code ->
            // Get from the Api.
            val baseDto = iRemoteRepo.getRaceDay(date, code)

            // Get the MeetingDto, there'll only be one but a list is returned.
            val meetingDto = baseDto.body.RaceDay.Meetings[0]

            // Process the list of Races associated with the Meeting.
            parseRaces(meetingDto).onEach { result ->
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
    }

    /**
     * Get the list of Races in preparation for process the associated Runners.
     * @param meetingDto: The MeetingDto object associated with the RaceDto.
     * @return A Flow of empty string on success, else the exception.
     */
    private suspend fun parseRaces(meetingDto: MeetingDto): Flow<DataResult<Any>> = flow {
        try {
            // Get the database row id of the equivalent Meeting in the database.
            val mId = iDbRepo.getMeetingId(meetingDto.MeetingId)
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
                    Exception("[SaveRunners.parseRaces] Unknown exception or error."))
                )
            }

            Log.e("TAG [SaveRunners.parseRaces]",
                exception.localizedMessage ?:
                     "[SaveRunners.parseRaces] Unknown exception or error.")
        }
    }

    /**
     * Create the list of (domain) Runners and write the database.
     * @param rId: The (database row) id of the associated Race.
     * @param raceDto: The RaceDto object.
     * @return A Flow of empty string on success, else the exception.
     */
    private suspend fun parseRunners(rId: Long, raceDto: RaceDto): Flow<DataResult<Any>> = flow {
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
                    Exception("[SaveRunners.parseRunners] Unknown exception or error."))
                )
            }

            Log.e(
                "TAG [SaveRunners.parseRunners]",
                exception.localizedMessage ?:
                "[SaveRunners.parseRaces] Unknown exception or error.")
        }
    }
}