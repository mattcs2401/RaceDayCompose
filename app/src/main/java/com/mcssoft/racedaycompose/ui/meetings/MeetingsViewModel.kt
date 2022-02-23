package com.mcssoft.racedaycompose.ui.meetings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class MeetingsViewModel @Inject constructor(
    private val raceDayUseCases: RaceDayUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MeetingsState())
    val state: State<MeetingsState> = _state

    private var initialiseJob: Job? = null

    init {
        val date = DateUtils().getDateToday()
        initialise(date)
    }

    fun onEvent(event: MeetingsEvent) {
        when(event) {
            is MeetingsEvent.Initialising -> {
                // Clean start; delete every thing, GET, and (re-)populate the Db.
            }
            is MeetingsEvent.Loading -> {
                //
            }
            is MeetingsEvent.SelectMeeting -> {
                //
            }
        }
    }

    private fun initialise(date: String) {
//        viewModelScope.launch(Dispatchers.IO) {
        raceDayUseCases.initialisation(date)
//            .onEach { mtgDto ->
//                _state.value = state.value.copy(
//                    meetings = mtgDto.
//                )
//            }
////            .onEach { notes ->
////                _state.value = state.value.copy(
////                    notes = notes,
////                    noteOrder = noteOrder
////                )
////            }//.launchIn(viewModelScope)
        val bp = "bp"
    }


}