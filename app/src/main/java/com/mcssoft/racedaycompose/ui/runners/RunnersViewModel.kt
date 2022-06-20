package com.mcssoft.racedaycompose.ui.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.destinations.RunnersScreenDestination
import com.mcssoft.racedaycompose.ui.races.RacesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RunnersViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnersState.loading())
    val state: StateFlow<RunnersState> = _state

    private var rceId = RunnersScreenDestination.argsFrom(savedStateHandle).raceId

    init {
        getRace(rceId)
        getRunners(rceId)
    }

    fun onEvent(event: RunnersEvent) {
        when(event) {
            is RunnersEvent.Cancel -> {
                // TBA.
            }
            is RunnersEvent.Retry -> {
                // TBA.
            }
            is RunnersEvent.CheckRunner -> {
                checkRunner(event.runnerId, event.checked)
            }
        }
    }

    private fun checkRunner(runnerId: Long, checked: Boolean) {
        raceDayUseCases.setRunnerChecked(runnerId, checked).onEach { result ->
            when {
                result.loading -> {}
                result.failed -> {
                    _state.value = _state.value.copy().apply {
                        result.exception?.let { exception ->
                            RacesState.failure(exception)
                        }
                    }
                }
                result.successful -> {
                    _state.value = _state.value.copy().apply {
                        runners.find { runner ->
                            runner._id == runnerId
                        }?.checked = checked
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRace(raceId: Long) {
        raceDayUseCases.getRace(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.value = _state.value.copy().apply {
                        RacesState.loading()
                    }
                }
                result.failed -> {
                    _state.value = _state.value.copy().apply {
                        result.exception?.let { exception ->
                            RacesState.failure(exception)
                        }
                    }
                }
                result.successful -> {
                    _state.value = _state.value.copy(
                        exception = null,
                        status = RunnersState.Status.Success,
                        loading = false,
                        rce = result.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRunners(raceId: Long) {
        raceDayUseCases.getRunners(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.value = _state.value.copy().apply {
                        RacesState.loading()
                    }
                }
                result.failed -> {
                    _state.value = _state.value.copy().apply {
                        result.exception?.let { exception ->
                            RacesState.failure(exception)
                        }
                    }                }
                result.successful -> {
                    _state.value = _state.value.copy(
                        exception = null,
                        status = RunnersState.Status.Success,
                        loading = false,
                        lRunners = result.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}