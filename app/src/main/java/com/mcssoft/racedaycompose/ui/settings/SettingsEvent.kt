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

    /**
     * Get the OnlyAuNz preference.
     */
    object GetOnlyAuNzPref: SettingsEvent()

    /**
     * Save the OnlyAuNz preference.
     * @param onlyAuNz: The value to save.
     */
    data class SaveOnlyAuNzPref(val onlyAuNz: Boolean): SettingsEvent()

}