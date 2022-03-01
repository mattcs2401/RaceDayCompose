package com.mcssoft.racedaycompose.utility

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.dto.MeetingDto
import com.mcssoft.racedaycompose.domain.dto.RaceDto
import com.mcssoft.racedaycompose.domain.dto.toMeeting
import com.mcssoft.racedaycompose.domain.dto.toRace
import com.mcssoft.racedaycompose.domain.model.Race
import javax.inject.Inject

class DbUtils @Inject constructor(
    private val remote: IRemoteRepo,
    private val local: IDbRepo
) {
    // Refresh (rewrite) the database with a new GET from the Api.
    suspend fun refresh(mtgDate: String, mtgType: String) {
        // Delete whatever is there (CASCADE should take care of Race/Runner etc).
        local.deleteMeetings()

        // GET from the Api.
        val rdDto = remote.getRaceDay(mtgDate).RaceDay

        // Loop through the list of MeetingDto.
        rdDto.Meetings.filter { it.MeetingType == mtgType }
            .forEach { meetingDto ->
                // Detail in the 1st Race used for Meeting (all other Races will have the same
                // detail).
                val raceDto = meetingDto.Races[0]
                // Write Meeting details.
                val mId = populateMeeting(meetingDto, raceDto)
                // Write Race details.
                populateRaces(mId, meetingDto.Races)
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
        return local.insertMeeting(meeting)
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
        return local.insertRaces(lRaces)
    }
}