package com.mcssoft.racedaycompose.domain.use_case.cases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.dto.*
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.DateUtils

/**
 * Take the values retrieved from the Api and save them to the database.
 */
class SaveFromApi @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(raceDayDto: RaceDayDto, mtgType: String): Flow<DataResult<String>> = flow {
        try {

            emit(DataResult.Loading())

            // Delete whatever is there (CASCADE should take care of Race/Runner etc).
            iDbRepo.deleteMeetings()

            // Loop through the list of MeetingDto.
            raceDayDto.Meetings.filter { type -> type.MeetingType == mtgType }
            .forEach { meetingDto ->
                // Weather/Track detail in the 1st Race used for Meeting (all other Races will have
                // the same info).
                val raceDto = meetingDto.Races[0]
                // Write Meeting details.
                val mId = populateMeeting(meetingDto, raceDto)
                // Write Race details.
                populateRaces(mId, meetingDto.Races)
            }

            emit(DataResult.Success(""))

        } catch (ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }

    }

    /**
     * Populate (insert) a Meeting into the database.
     * @param meetingDto: The Meeting to insert (mapped from Dto domain to Model by extension
     *                    function on the Dto object).
     * @param raceDto:
     * @return The row _id of the inserted Meeting.
     */
    private suspend fun populateMeeting(meetingDto: MeetingDto, raceDto: RaceDto): Long {
        // Initial create of the Meeting.
        val meeting = meetingDto.toMeeting()
        // Add in the additional "summary" values.
        meeting.meetingTime = DateUtils().getTime(raceDto.RaceTime)
        meeting.racesNo = meetingDto.Races.count()
        meeting.weatherCond = raceDto.WeatherCondition
        meeting.trackCond = raceDto.TrackCondition
        meeting.trackRating = raceDto.TrackRating
        // Insert.
        return iDbRepo.insertMeeting(meeting)
    }

    /**
     * Populate (bulk insert) a list of Races into the database.
     * @param mId: The Meeting row _id from the Meeting's previous insert.
     * @param dtoRaces: The list of Races to insert (mapped from Dto domain to Model by extension
     *                  function on the Dto object).
     * @return A list of the row _ids of the inserted Races (usage TBA)
     */
    private suspend fun populateRaces(mId: Long, dtoRaces: List<RaceDto>): List<Long> {
        val lRaces = mutableListOf<Race>()
        dtoRaces.forEach { dtoRace ->
            val race = dtoRace.toRace(mId)
            lRaces.add(race)
        }
        return iDbRepo.insertRaces(lRaces)
    }
}