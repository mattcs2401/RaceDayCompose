package com.mcssoft.racedaycompose.ui.settings

import android.provider.Settings
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _state = mutableStateOf(SettingsState())
    val state: State<SettingsState> = _state

    init {
        initialise()
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.GetFromDbPref -> {
                getFromDbPref()
            }
            is SettingsEvent.SaveFromDbPref -> {
                saveFromDbPref(event.fromDb)
            }
            is SettingsEvent.GetOnlyAuPref -> {

            }
            is SettingsEvent.SaveOnlyAuPref -> {

            }
        }
    }

    private fun initialise() {
        getFromDbPref()
        // TBA others ?
    }

    private fun getFromDbPref() {                          // TODO setting type ?
        raceDayUseCases.getSettings().onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    //_state.value = SettingsState(loading = true)
                    _state.value  = _state.value.copy(
                        loading = true, error = "", fromDbPref = false
                    )
                }
                is DataResult.Error -> {
                    //_state.value = SettingsState(error = result.message)
                    _state.value  = _state.value.copy(
                        loading = false, error = result.message, fromDbPref = false
                    )
                }
                is DataResult.Success -> {
                    //_state.value = SettingsState(fromDbPref = result.data as Boolean)
                    _state.value  = _state.value.copy(
                        loading = false, error = "", fromDbPref = result.data as Boolean
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFromDbPref(fromDb: Boolean) {
        raceDayUseCases.saveSettings(fromDb).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    //_state.value = SettingsState(loading = true)
                    _state.value  = _state.value.copy(
                        loading = true, error = "", fromDbPref = false
                    )
                }
                is DataResult.Error -> {
                    //_state.value = SettingsState(error = result.message)
                    _state.value  = _state.value.copy(
                        loading = false, error = result.message, fromDbPref = false
                    )
                }
                is DataResult.Success -> {
                    //_state.value = SettingsState(fromDbPref = result.data as Boolean)
                    _state.value  = _state.value.copy(
                        loading = false, error = "", fromDbPref = result.data as Boolean
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}