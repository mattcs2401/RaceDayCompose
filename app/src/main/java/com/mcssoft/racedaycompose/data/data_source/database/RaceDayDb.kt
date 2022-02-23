package com.mcssoft.racedaycompose.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.domain.model.Runner

@Database(entities = [Meeting::class, Race::class, Runner::class],
        version = 1,
        exportSchema = false)
abstract class RaceDayDb : RoomDatabase() {

    abstract fun getRaceDayDao(): IDbRepo

}