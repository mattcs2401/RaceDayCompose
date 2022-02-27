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
            .forEach { dtoMeeting ->
                val mId = populateMeeting(dtoMeeting)
                populateRaces(mId, dtoMeeting.Races)
            }
    }

    /**
     * Populate (insert) a Meeting into the database.
     * @param dtoMeeting: The Meeting to insert (mapped from Dto domain to Model by extension
     *                    function on the Dto object).
     * @return The row _id of the inserted Meeting.
     */
    private suspend fun populateMeeting(dtoMeeting: MeetingDto): Long {
        return local.insertMeeting(dtoMeeting.toMeeting())
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