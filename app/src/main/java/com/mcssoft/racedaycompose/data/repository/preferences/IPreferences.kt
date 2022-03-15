package com.mcssoft.racedaycompose.data.repository.preferences

interface IPreferences {

    suspend fun getPreference(prefType: PreferenceType): Any

    suspend fun setPreference(prefType: PreferenceType, value: Any)
}