package com.mcssoft.racedaycompose.ui.runners

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.ui.destinations.RunnersScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
        _state.update { it.copy(raceId = raceId) }
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
                checkRunner(
                    event.runnerId,
                    event.checked
                )
            }
            is RunnersEvent.SetForSummary -> {
                setForSummary(
                    event.raceId,
                    event.runnerId,
                    event.checked
                )
            }
        }
    }

    /**
     * Set the checkbox on the Runner record (used for the Summary).
     * @param runnerId: The Runner id.
     * @param checked: The checkbox value.
     */
    private fun checkRunner(runnerId: Long, checked: Boolean) {
        raceDayUseCases.setRunnerChecked(runnerId, checked).onEach { result ->
            when {
                result.loading -> {}
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception,
                            status = RunnersState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy().apply {
                            lRunners.find { runner -> runner._id == runnerId }?.checked = checked
                            checkedId = runnerId
                            chked = checked
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get Race details.
     * @param raceId: The Race id.
     */
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
                            exception = result.exception,
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
                            race = result.data
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Get Runners associated with a Race.
     * @param raceId: The Race id (is a foreign key on Runner).
     */
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
                            exception = result.exception,
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

    /**
     * Class to ensure the backing data for the Summary is set (if exists).
     */
    private fun setForSummary(raceId: Long, runnerId: Long, checked: Boolean) {
        viewModelScope.launch {
            raceDayUseCases.setForSummary(raceId, runnerId, checked).collect { result ->
                when {
                    result.loading -> {
                        _state.update { state -> state.copy(exception = null, loading = true) }
                    }
                    result.failed -> {
                        _state.update { state ->
                            state.copy(exception = state.exception, loading = false)
                        }
                    }
                    result.successful -> {
                        _state.update { state -> state.copy(exception = null, loading = false ) }
                    }
                }
            } // collect.
        } // view model scope.
    }
}