package com.mcssoft.racedaycompose.data.repository.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Class to set app preferences in a datastore.
 */
class PreferencesImpl @Inject constructor (context: Context): IPreferences {

    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

    private var dsPrefs: DataStore<Preferences> = context.dataStore

    private val fromDbKey = booleanPreferencesKey("fromDbKey")

    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
    /**
     * Get the "FromDb" preference.
     */
    override suspend fun getFromDbPref(): Boolean {
        return dsPrefs.data.first()[fromDbKey] ?: false
    }

    /**
     * Set the "FromDb" preference.
     * @param value: The value to set.
     */
    override suspend fun saveFromDbPref(value: Boolean) {
        dsPrefs.edit { preferences ->
            preferences[fromDbKey] = value
        }
    }
    //</editor-fold>

}