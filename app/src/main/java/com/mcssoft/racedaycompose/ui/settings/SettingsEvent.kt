package com.mcssoft.racedaycompose.ui.settings

sealed class SettingsEvent {

    /**
     * Get the FromDb preference.
     */
    object GetFromDbPref: SettingsEvent()

    /**
     * Save the FromDb preference.
     * @param fromDb: The value to save.
     */
    data class SaveFromDbPref(val fromDb: Boolean): SettingsEvent()


    object GetOnlyAuPref: SettingsEvent()

    data class SaveOnlyAuPref(val onlyAu: Boolean): SettingsEvent()


}