package com.mcssoft.racedaycompose.data.repository.preferences

import kotlinx.coroutines.flow.Flow

interface IPreferences {

    suspend fun getFromDbPref(): Boolean

    suspend fun saveFromDbPref(value: Boolean)

}