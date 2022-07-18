package com.mcssoft.racedaycompose.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState.initialise())
    val state: StateFlow<SplashState> = _state

    init {
        checkPrePopulate()
    }

    private fun checkPrePopulate() {
        raceDayUseCases.checkPrePopulate().onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = SplashState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Success,
                            loading = false,
                            prePopulated = (result.data == true)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun prePopulate() {
        raceDayUseCases.prePopulate().onEach { result ->
            when {
                result.loading -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Loading,
                            loading = true
                        )
                    }
                }
                result.failed -> {
                    _state.update { state ->
                        state.copy(
                            exception = result.exception ?: Exception("An unknown error has occurred."),
                            status = SplashState.Status.Failure,
                            loading = false
                        )
                    }
                }
                result.successful -> {
                    _state.update { state ->
                        state.copy(
                            exception = null,
                            status = SplashState.Status.Success,
                            loading = false,
                            prePopulated = (result.data == true)
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}