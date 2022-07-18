package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupBaseFromApi
import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupRunnerFromApi
import com.mcssoft.racedaycompose.domain.use_case.cases.meetings.GetMeeting
import com.mcssoft.racedaycompose.domain.use_case.cases.meetings.GetMeetings
import com.mcssoft.racedaycompose.domain.use_case.cases.preferences.GetPreferences
import com.mcssoft.racedaycompose.domain.use_case.cases.preferences.SavePreferences
import com.mcssoft.racedaycompose.domain.use_case.cases.races.GetRace
import com.mcssoft.racedaycompose.domain.use_case.cases.races.GetRaces
import com.mcssoft.racedaycompose.domain.use_case.cases.runners.GetRunners
import com.mcssoft.racedaycompose.domain.use_case.cases.runners.SetRunnerChecked
import com.mcssoft.racedaycompose.domain.use_case.cases.splash.CheckPrePopulate
import com.mcssoft.racedaycompose.domain.use_case.cases.splash.PrePopulate
import com.mcssoft.racedaycompose.domain.use_case.cases.summary.GetSummaries
import com.mcssoft.racedaycompose.domain.use_case.cases.summary.SetForSummary

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

    // Check/uncheck the "checked" metadata element on the Runner record.
    val setRunnerChecked: SetRunnerChecked,

    // Get Settings.
    val getPreferences: GetPreferences,

    // Save Settings.
    val savePreferences: SavePreferences,

    // Get the Summary.
    val getSummaries: GetSummaries,

    // Update the summary.
    val setForSummary: SetForSummary,

    // A quick check that Trainer/Horse tables are (pre)populated.
    val checkPrePopulate: CheckPrePopulate,

    // Pre-populate Trainer/Horse tables.
    val prePopulate: PrePopulate
)