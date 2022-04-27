package com.mcssoft.racedaycompose.ui.runners

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RunnersViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _runnersState = mutableStateOf(RunnersState())
    val runnersState: State<RunnersState> = _runnersState

    init {
        savedStateHandle.get<Long>(Constants.KEY_RACE_ID)?.let { raceId ->
            getRace(raceId)
            getRunners(raceId)
        }
    }

    fun onEvent(runnerId: Long, checked: Boolean) {
        checkRunner(runnerId, checked)
    }

    private fun checkRunner(runnerId: Long, checked: Boolean) {
        raceDayUseCases.setRunnerChecked(runnerId, checked).onEach { result ->
            when {
                result.failed -> {
                    _runnersState.value = _runnersState.value.copy(
                        error = result.exception?.localizedMessage
                            ?: "[getRace] An unknown error or exception occurred.",
                        loading = false
                    )
                }
                result.successful -> {
                    _runnersState.value.runners.find { runner ->
                        runner._id == runnerId
                    }?.checked = checked
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRace(raceId: Long) {
        raceDayUseCases.getRace(raceId).onEach { result ->
            when {
                result.loading -> {
                    _runnersState.value.loading = true
                }
                result.failed -> {
                    _runnersState.value.error = result.exception?.localizedMessage
                        ?: "[getRace] An unknown error or exception occurred."
                    _runnersState.value.loading = false
                }
                result.successful -> {
                    _runnersState.value.race = result.data
                    _runnersState.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRunners(raceId: Long) {
        raceDayUseCases.getRunners(raceId).onEach { result ->
            when {
                result.loading -> {
                    _runnersState.value.loading = true
                }
                result.failed -> {
                    _runnersState.value.error = result.exception?.localizedMessage
                        ?: "[getRunners] An unknown error or exception occurred."
                    _runnersState.value.loading = false
                }
                result.successful -> {
                    _runnersState.value.runners = result.data ?: emptyList()
                    _runnersState.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }
}