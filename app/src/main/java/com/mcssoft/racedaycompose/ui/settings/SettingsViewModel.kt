package com.mcssoft.racedaycompose.ui.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.Preference
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _fromDbState = mutableStateOf(FromDbState())
    val fromDbState: State<FromDbState> = _fromDbState

    private val _onlyAuNzState = mutableStateOf(OnlyAuNzState())
    val onlyAuNzState: State<OnlyAuNzState> = _onlyAuNzState

    init {
        initialise()
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.GetFromDbPref -> {
                getFromDbPref(Preference.FromDbPref)
            }
            is SettingsEvent.SaveFromDbPref -> {
                saveFromDbPref(Preference.FromDbPref, event.fromDb)
            }
            is SettingsEvent.GetOnlyAuNzPref -> {
                getOnlyAuNzPref(Preference.OnlyAuNzPref)
            }
            is SettingsEvent.SaveOnlyAuNzPref -> {
                saveOnlyAuNzPref(Preference.OnlyAuNzPref, event.onlyAuNz)
            }
        }
    }

    /**
     * Set the values for the preferences state.
     */
    private fun initialise() {
        getFromDbPref(Preference.FromDbPref)
        getOnlyAuNzPref(Preference.OnlyAuNzPref)
        // TBA others ?
    }

    //<editor-fold default state="collapsed" desc="Region: FromDb preference">
    private fun getFromDbPref(pref: Preference.FromDbPref) {
        raceDayUseCases.getPreferences(pref).onEach { result ->
            when {
                result.loading -> {
                    _fromDbState.value =
                        FromDbState(loading = true, error = "", preference = false)
                }
                result.failed -> {
                    _fromDbState.value =
                        FromDbState(
                            loading = false,
                            error = result.exception?.localizedMessage ?:
                                "An unknown error or exception occurred.",
                            preference = false)
                }
                result.successful -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFromDbPref(pref: Preference.FromDbPref, fromDb: Boolean) {
        raceDayUseCases.savePreferences(pref, fromDb).onEach { result ->
            when {
                result.loading -> {
                    _fromDbState.value =
                        FromDbState(loading = true, error = "", preference = false)
                }
                result.failed -> {
                    _fromDbState.value =
                        FromDbState(
                            loading = false,
                            error = result.exception?.localizedMessage ?:
                                "An unknown error or exception occurred.",
                            preference = false)
                }
                result.successful -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: OnlyAuNz preference">
    private fun getOnlyAuNzPref(pref: Preference.OnlyAuNzPref) {
        raceDayUseCases.getPreferences(pref).onEach { result ->
            when {
                result.loading -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = true, error = "", preference = false)
                }
                result.failed -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(
                            loading = false,
                            error = result.exception?.localizedMessage ?:
                                "An unknown error or exception occurred.",
                            preference = false)
                }
                result.successful -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveOnlyAuNzPref(pref: Preference.OnlyAuNzPref, onlyAuNz: Boolean) {
        raceDayUseCases.savePreferences(pref, onlyAuNz).onEach { result ->
            when {
                result.loading -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = true, error = "", preference = false)
                }
                result.failed -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(
                            loading = false,
                            error = result.exception?.localizedMessage ?:
                                "An unknown error or exception occurred.",
                            preference = false)
                }
                result.successful -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>
}