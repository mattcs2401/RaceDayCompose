package com.mcssoft.racedaycompose.ui.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.DataResult
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
                getFromDbPref(PreferenceType.FromDbPref)
            }
            is SettingsEvent.SaveFromDbPref -> {
                saveFromDbPref(PreferenceType.FromDbPref, event.fromDb)
            }
            is SettingsEvent.GetOnlyAuNzPref -> {
                getOnlyAuNzPref(PreferenceType.OnlyAuNzPref)
            }
            is SettingsEvent.SaveOnlyAuNzPref -> {
                saveOnlyAuNzPref(PreferenceType.OnlyAuNzPref, event.onlyAuNz)
            }
        }
    }

    /**
     * Set the values for the preferences state.
     */
    private fun initialise() {
        getFromDbPref(PreferenceType.FromDbPref)
        getOnlyAuNzPref(PreferenceType.OnlyAuNzPref)
        // TBA others ?
    }

    //<editor-fold default state="collapsed" desc="Region: FromDb preference">
    private fun getFromDbPref(prefType: PreferenceType.FromDbPref) {
        raceDayUseCases.getPreferences(prefType).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _fromDbState.value =
                        FromDbState(loading = true, error = "", preference = false)
                }
                is DataResult.Error -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = result.message, preference = false)
                }
                is DataResult.Success -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFromDbPref(prefType: PreferenceType.FromDbPref, fromDb: Boolean) {
        raceDayUseCases.savePreferences(prefType, fromDb).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _fromDbState.value =
                        FromDbState(loading = true, error = "", preference = false)
                }
                is DataResult.Error -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = result.message, preference = false)
                }
                is DataResult.Success -> {
                    _fromDbState.value =
                        FromDbState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: OnlyAuNz preference">
    private fun getOnlyAuNzPref(prefType: PreferenceType.OnlyAuNzPref) {
        raceDayUseCases.getPreferences(prefType).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = true, error = "", preference = false)
                }
                is DataResult.Error -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = result.message, preference = false)
                }
                is DataResult.Success -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveOnlyAuNzPref(prefType: PreferenceType.OnlyAuNzPref, onlyAuNz: Boolean) {
        raceDayUseCases.savePreferences(prefType, onlyAuNz).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = true, error = "", preference = false)
                }
                is DataResult.Error -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = result.message, preference = false)
                }
                is DataResult.Success -> {
                    _onlyAuNzState.value =
                        OnlyAuNzState(loading = false, error = "", preference = result.data as Boolean)
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>
}