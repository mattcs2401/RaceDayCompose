package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.domain.use_case.cases.*
import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupBaseFromApi
import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupRunnerFromApi

data class RaceDayUseCases(

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupBaseFromApi: SetupBaseFromApi,

    // Get and save the base set of data from the Api (Meetings and Races).
    val setupRunnerFromApi: SetupRunnerFromApi,

    // Get a list of Meetings from the database.
    val getMeetings: GetMeetings,

    // Get a single Meeting from the database.
    val getMeeting: GetMeeting,

    // Get a list of the Races from the database.
    val getRaces: GetRaces,

    // Get a Race from the database.
    val getRace: GetRace,

    // Get a list of the Runners from the database.
    val getRunners: GetRunners,

    // Get Settings.
    val getPreferences: GetPreferences,

    // Save Settings.
    val savePreferences: SavePreferences

)