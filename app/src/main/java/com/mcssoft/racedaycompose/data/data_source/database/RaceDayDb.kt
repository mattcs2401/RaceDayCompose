package com.mcssoft.racedaycompose.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.*

@Database(
    entities = [
        Meeting::class,
        Race::class,
        Runner::class,
        Summary::class,
        Trainer::class,
        Horse::class,
        Admin::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RaceDayDb : RoomDatabase() {

    abstract fun getRaceDayDao(): IDbRepo

}