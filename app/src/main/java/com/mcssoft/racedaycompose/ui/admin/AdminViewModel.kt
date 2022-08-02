package com.mcssoft.racedaycompose.ui.admin

import androidx.lifecycle.ViewModel
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

}