package com.mcssoft.racedaycompose.data.repository.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mcssoft.racedaycompose.R
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Class to set app preferences in a datastore.
 */
class PreferencesImpl @Inject constructor (context: Context): IPreferences {

    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "settings")

    private var dsPrefs: DataStore<Preferences> = context.dataStore

    // Keys for get/set preferences.
    private val fromDbKey =
        booleanPreferencesKey(context.resources.getString(R.string.pref_from_db_key))
    private val onlyAuNzKey =
        booleanPreferencesKey(context.resources.getString(R.string.pref_only_aunz_key))

    /**
     *
     */
    override suspend fun getPreference(prefType: PreferenceType): Any {
        return when(prefType) {
            is PreferenceType.FromDbPref -> {
                getFromDbPref()
            }
            is PreferenceType.OnlyAuNzPref -> {
                getOnlyAuNzPref()
            }
        }
    }

    /**
     *
     */
    override suspend fun setPreference(prefType: PreferenceType, value: Any) {
        when(prefType) {
            is PreferenceType.FromDbPref -> {
                setFromDbPref(value as Boolean)
            }
            is PreferenceType.OnlyAuNzPref -> {
                setOnlyAuNzPref(value as Boolean)
            }
        }
    }


    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
    /**
     * Get the "FromDb" preference.
     */
    private suspend fun getFromDbPref(): Boolean {
        return dsPrefs.data.first()[fromDbKey] ?: false
    }

    /**
     * Set the "FromDb" preference.
     * @param value: The value to set.
     */
    private suspend fun setFromDbPref(value: Boolean) {
        dsPrefs.edit { preferences ->
            preferences[fromDbKey] = value
        }
    }

    /**
     * Get the "OnlyAuNz" preference.
     */
    private suspend fun getOnlyAuNzPref(): Boolean {
        return dsPrefs.data.first()[onlyAuNzKey] ?: false
    }

    /**
     * Set the "OnlyAuNz" preference.
     * @param value: The value to set.
     */
    private suspend fun setOnlyAuNzPref(value: Boolean) {
        dsPrefs.edit { preferences ->
            preferences[onlyAuNzKey] = value
        }
    }

    //</editor-fold>

}