package com.mcssoft.racedaycompose.ui.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SplashState())
    val state: State<SplashState> = _state

    init {
        val date = DateUtils().getDateToday()
        initialise(date)
    }

    private fun initialise(date: String) {
        raceDayUseCases.initialisation(date).onEach { result ->
            when(result) {
                is DataResult.Loading -> {
                    _state.value = SplashState(
                        error = "",
                        finished = false,
                        initialising = true,
                        )
                }
                is DataResult.Error -> {
                    _state.value = SplashState(
                        error = result.message!!,
                        finished = false,
                        initialising = false
                        )
                }
                is DataResult.Success -> {
                    _state.value = SplashState(
                        error = "",
                        finished = true,
                        initialising = false)
                }
            }
        }.launchIn(viewModelScope)
    }

}