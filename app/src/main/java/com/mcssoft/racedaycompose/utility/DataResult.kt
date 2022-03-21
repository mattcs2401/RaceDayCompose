package com.mcssoft.racedaycompose.utility

sealed class DataResult<T> (
    var data: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : DataResult<T>(data)

    class Error<T>(exception: java.lang.Exception, data: T? = null) : DataResult<T>(data, exception)

    class Loading<T>(data: T? = null) : DataResult<T>(data)

    val failure: Boolean
        get() = exception != null
}

