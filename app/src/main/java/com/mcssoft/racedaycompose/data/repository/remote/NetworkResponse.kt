package com.mcssoft.racedaycompose.data.repository.remote

import retrofit2.Response

/**
 * Data class to wrap the Retrofit Response.
 * Based on: https://www.youtube.com/watch?v=KWJH7Ns1Bfk
 */
data class NetworkResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val ex: Exception?,
    val err: String,
) {
    companion object {
        fun <T> success(data: Response<T>): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Success,
                data = data,
                ex = null,
                err = ""
            )
        }

        fun <T> exception(exception: Exception): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Exception,
                data = null,
                ex = exception,
                err = ""
            )
        }

        fun <T> error(message: String = ""): NetworkResponse<T> {
            return NetworkResponse(
                status = Status.Error,
                data = null,
                ex = null,
                err = message
            )
        }
    }

    sealed class Status {
        object Success : Status()
        object Exception : Status()
        object Error : Status()
    }

    val exception: Boolean
        get() = this.status == Status.Exception

    val error: Boolean
        get() = !exception && this.data?.isSuccessful == false

    val errorMsg: String
        get() = this.err

    val successful: Boolean
        get() = !exception && this.data?.isSuccessful == true

    val body: T
        get() = this.data?.body()!!
}