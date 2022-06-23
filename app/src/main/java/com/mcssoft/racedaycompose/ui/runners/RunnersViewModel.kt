package com.mcssoft.racedaycompose.ui.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.destinations.RunnersScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RunnersViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(RunnersState.initialise())
    val state: StateFlow<RunnersState> = _state

    private var raceId = RunnersScreenDestination.argsFrom(savedStateHandle).raceId

    init {
        _state.update { it.copy(rceId = raceId) }
        getRace(raceId)
        getRunners(raceId)
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
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = RunnersState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy().apply {
                            runners.find { runner ->
                                runner._id == runnerId
                            }?.checked = checked
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRace(raceId: Long) {
        raceDayUseCases.getRace(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RunnersState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = RunnersState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RunnersState.Status.Success,
                            loading = false,
                            rce = result.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRunners(raceId: Long) {
        raceDayUseCases.getRunners(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RunnersState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = RunnersState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = RunnersState.Status.Success,
                            loading = false,
                            lRunners = result.data ?: emptyList()
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}