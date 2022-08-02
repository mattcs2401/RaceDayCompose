package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(
    var exception: Exception?,
    var status: Status,
    var loading: Boolean = false,
    var data: List<Meeting>?
) {
    companion object {
        // Simply a Flow initializer.
        fun initialise(): MeetingsState {
            return MeetingsState(
                exception = null,
                status = Status.Initialise,
                loading = false,
                data = null
            )
        }
    }

    sealed class Status {
        object Initialise : Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    val body: List<Meeting>
        get() = this.data ?: emptyList()
}