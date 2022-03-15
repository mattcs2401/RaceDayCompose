package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.domain.use_case.cases.*

data class RaceDayUseCases(

    // Get the base set of data from the Api.
    val getFromApi: GetFromApi,

    // Save a filtered set of the base data to the database.
    val saveFromApi: SaveFromApi,

    // Get a single Meeting from the database.
    val getMeeting: GetMeeting,

    // Get a list of Meetings from the database.
    val getMeetings: GetMeetings,

    // Get a list of the Races from the database.
    val getRaces: GetRaces,

    // Get a list of the Runners from the database.
    val getRunners: GetRunners,

    // Get Settings.
    val getPreferences: GetPreferences,

    // Save Settings.
    val savePreferences: SavePreferences
)