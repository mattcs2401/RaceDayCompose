package com.mcssoft.racedaycompose.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.data.repository.preferences.Preference
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _fromDbState = MutableStateFlow(FromDbState.initialise())
    //val fromDbState: StateFlow<FromDbState> = _fromDbState
    val fromDbState = _fromDbState.asStateFlow()

    private val _onlyAuNzState = MutableStateFlow(OnlyAuNzState.initialise())
    //val onlyAuNzState: StateFlow<OnlyAuNzState> = _onlyAuNzState
    val onlyAuNzState = _onlyAuNzState.asStateFlow()

    init {
        getFromDbPref(Preference.FromDbPref)
        getOnlyAuNzPref(Preference.OnlyAuNzPref)
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
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

    //<editor-fold default state="collapsed" desc="Region: FromDb preference">
    private fun getFromDbPref(pref: Preference.FromDbPref) {
        raceDayUseCases.getPreferences(pref).onEach { result ->
            when {
//                result.loading -> {
//                    _fromDbState.value = _fromDbState.value.copy().apply {
//                        FromDbState.loading()
//                    }
//                }
                result.failed -> {
                    _fromDbState.value = _fromDbState.value.copy().apply {
                        val exception =
                            result.exception ?: Exception("An unknown error or exception occurred.")
                        FromDbState.failure(exception)
                    }
                }
                result.successful -> {
                    _fromDbState.update { state ->
                        state.copy(
                            loading = false,
                            exception = null,
                            currValue = result.data as Boolean
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFromDbPref(pref: Preference.FromDbPref, fromDb: Boolean) {
        raceDayUseCases.savePreferences(pref, fromDb).onEach { result ->
            when {
//                result.loading -> {
//                    _fromDbState.value = _fromDbState.value.copy().apply {
//                        FromDbState.loading()
//                    }
//                }
                result.failed -> {
                    _fromDbState.value = _fromDbState.value.copy().apply {
                        val exception =
                            result.exception ?: Exception("An unknown error or exception occurred.")
                        FromDbState.failure(exception)
                    }
                }
                result.successful -> {
                    _fromDbState.update { state ->
                        state.copy(
                            loading = false,
                            exception = null,
                            currValue = result.data as Boolean
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>

    //<editor-fold default state="collapsed" desc="Region: OnlyAuNz preference">
    private fun getOnlyAuNzPref(pref: Preference.OnlyAuNzPref) {
        raceDayUseCases.getPreferences(pref).onEach { result ->
            when {
//                result.loading -> {
//                    _onlyAuNzState.value = _onlyAuNzState.value.copy().apply {
//                        FromDbState.loading()
//                    }
//                }
                result.failed -> {
                    _onlyAuNzState.value = _onlyAuNzState.value.copy().apply {
                        val exception =
                            result.exception ?: Exception("An unknown error or exception occurred.")
                        FromDbState.failure(exception)
                    }
                }
                result.successful -> {
                    _onlyAuNzState.update { state ->
                        state.copy(
                            loading = false,
                            exception = null,
                            currValue = result.data as Boolean
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveOnlyAuNzPref(pref: Preference.OnlyAuNzPref, onlyAuNz: Boolean) {
        raceDayUseCases.savePreferences(pref, onlyAuNz).onEach { result ->
            when {
//                result.loading -> {
//                    _onlyAuNzState.value = _onlyAuNzState.value.copy().apply {
//                        FromDbState.loading()
//                    }
//                }
                result.failed -> {
                    _onlyAuNzState.value = _onlyAuNzState.value.copy().apply {
                        val exception =
                            result.exception ?: Exception("An unknown error or exception occurred.")
                        FromDbState.failure(exception)
                    }
                }
                result.successful -> {
                    _onlyAuNzState.update { state ->
                        state.copy(
                            loading = false,
                            exception = null,
                            currValue = result.data as Boolean
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
    //</editor-fold>
}