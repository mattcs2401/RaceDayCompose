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
): ViewModel()
{
    private val _state = mutableStateOf(RunnersState())
    val state: State<RunnersState> = _state

    init {
        savedStateHandle.get<Long>(Constants.KEY_RACE_ID)?.let { raceId ->
            getRace(raceId)
            getRunners(raceId)
        }
    }

    private fun getRace(raceId: Long) {
        raceDayUseCases.getRace(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.value.loading = true
                }
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                            "[getRace] An unknown error or exception occurred."
                    _state.value.loading = false
                }
                result.successful -> {
                    _state.value.race = result.data
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getRunners(raceId: Long) {
        raceDayUseCases.getRunners(raceId).onEach { result ->
            when {
                result.loading -> {
                    _state.value.loading = true
                }
                result.failed -> {
                    _state.value.error = result.exception?.localizedMessage ?:
                            "[getRunners] An unknown error or exception occurred."
                    _state.value.loading = false
                }
                result.successful -> {
                    _state.value.runners = result.data ?: emptyList()
                    _state.value.loading = false
                }
            }
        }.launchIn(viewModelScope)
    }
}