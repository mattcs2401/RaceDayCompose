package com.mcssoft.racedaycompose.ui.meetings

import com.mcssoft.racedaycompose.domain.model.Meeting

data class MeetingsState(

    var exception: Exception?,
    var status: Status,
    var data: List<Meeting>?
)
{
    companion object {
        fun initialise(): MeetingsState {
            return MeetingsState(
                status = Status.Initialise,
                data = null,
                exception = null
            )
        }

        fun success(data: List<Meeting>): MeetingsState {
            return MeetingsState(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun failure(exception: Exception): MeetingsState {
            return MeetingsState(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }

        fun loading(): MeetingsState {
            return MeetingsState(
                status = Status.Loading,
                data = null,
                exception = null
            )
        }
    }

    sealed class Status {
        object Success : Status()
        object Failure : Status()
        object Loading: Status()
        object Initialise: Status()
    }

    val loading: Boolean
        get() = this.status == Status.Loading

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
        get() = (!failed) && (this.data != null) && (this.data?.isNotEmpty() == true)

    val body: List<Meeting>
        get() = this.data ?: emptyList()
}