package com.mcssoft.racedaycompose.utility

sealed class DataResult<T> (
    val data: T? = null,

    // Default to an empty string so we can check if it was populated by an exception message or
    // some some sort of informational message, e.g. nothing found.
    val message: String = ""
) {
    class Success<T>(data: T) : DataResult<T>(data)

    class Error<T>(message: String, data: T? = null) : DataResult<T>(data, message)

    class Loading<T>(data: T? = null) : DataResult<T>(data)

}

