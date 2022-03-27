package com.mcssoft.racedaycompose.data.repository.preferences

interface IPreferences {

    suspend fun getPreference(pref: Preference): Any

    suspend fun setPreference(pref: Preference, value: Any)
}