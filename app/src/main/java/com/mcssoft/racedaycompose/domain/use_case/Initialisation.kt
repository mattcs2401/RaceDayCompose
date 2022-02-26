package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.dto.MeetingDto
import com.mcssoft.racedaycompose.domain.dto.RaceDto
import com.mcssoft.racedaycompose.domain.dto.toMeeting
import com.mcssoft.racedaycompose.domain.dto.toRace
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Class that does a clean install/re-install. Basically:
 * 1. Delete anything already in the database.
 * 2. GET (Meeting/Race) from the Api.
 * 3. Re-populate the database.
 * @note: The single action "Initialisation" via the invoke() operator actually comprises several
 *        elements, because we are also using the database as a cache, and need to establish that
 *        from the Api result. Subsequent calls for UI screen data will get from the database.
 */
class Initialisation @Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo
) {
    // TODO - add try/catch, some sort or error notification ?
    /**
     * @param mtgDate: The date to use in the Api Url.
     * @param mtgType: The MeetingType to filter on, defaults to "R" (but "G and "T" also possible).
     * @return A Flow of DataResult (basically just signify; loading, success or failure).
     */
    operator fun invoke (mtgDate: String, mtgType: String = "R"): Flow<DataResult<String>> = flow {
        try {
            emit(DataResult.Loading())

            // Delete whatever is there (CASCADE should take care of Race/Runner etc).
            iDbRepo.deleteMeetings()
            // GET from the Api.
            val rdDto = iRemoteRepo.getRaceDay(mtgDate).RaceDay
            // Loop through the list of MeetingDto.
            rdDto.Meetings.filter { it.MeetingType == mtgType }
            .forEach { dtoMeeting ->
                val mId = populateMeeting(dtoMeeting)
                populateRaces(mId, dtoMeeting.Races)
            }

            emit(DataResult.Success(""))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }
    }

    private suspend fun populateMeeting(dtoMeeting: MeetingDto): Long {
        return iDbRepo.insertMeeting(dtoMeeting.toMeeting())
    }

    private suspend fun populateRaces(mId: Long, dtoRaces: List<RaceDto>): List<Long> {
        val lRaces = mutableListOf<Race>()
        dtoRaces.forEach { dtoRace ->
            val race = dtoRace.toRace(mId)
            lRaces.add(race)
        }
        return iDbRepo.insertRaces(lRaces)
    }

}