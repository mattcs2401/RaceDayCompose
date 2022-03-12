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

//    //<editor-fold default state="collapsed" desc="Region: Metadata">
//    /**
//     * Save a (long) value to the datastore.
//     * @param key: The key that is associated with the value.
//     * @param value: The value to save.
//     */
//    suspend fun saveMeetingId(key: String, value: Long) {
//        val dsKey = longPreferencesKey(key)
//        dsPrefs.edit { prefs ->
//            prefs[dsKey] = value
//        }
//    }
//
//    /**
//     * Get a (long) value from the datastore.
//     * @param key: The associated key.
//     * @return The value, else -1.
//     */
//    suspend fun getMeetingId(key: String): Long {
//        val dsKey = longPreferencesKey(key)
//        return dsPrefs.data.first()[dsKey] ?: -1
//    }
//    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: User selectable preferences">
    /**
     * Get the "fromDb" preference.
     * @usage (Example)
     * val fromDb = datastore.getFromDb.collectAsState(initial = false)
     * if(fromDb.value!!) { do something }
     */
    override suspend fun getFromDbPref(): Boolean {
        return dsPrefs.data.first()[fromDbKey] ?: false
    }

    /**
     * Set the "fromDb" preference.
     * @param value: The value to set.
     * @notes Must be called from within a (coroutine) scope.
     */
    override suspend fun saveFromDbPref(value: Boolean) {
        dsPrefs.edit { preferences ->
            preferences[fromDbKey] = value
        }
    }




    //</editor-fold>

}