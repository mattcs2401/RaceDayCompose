package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.domain.use_case.cases.GetMeetings
import com.mcssoft.racedaycompose.domain.use_case.cases.GetRaces
import com.mcssoft.racedaycompose.domain.use_case.cases.Initialisation
import com.mcssoft.racedaycompose.domain.use_case.cases.RefreshMeetings

data class RaceDayUseCases(
    // App first run initialisation.
    val initialisation: Initialisation,
    // Get a list of Meetings.
    val getMeetings: GetMeetings,
    // Refresh the list of Meetings (get from Api and rewrite the database).
    val refreshMeetings: RefreshMeetings,
    // Get a list of the Races.
    val getRaces: GetRaces
)