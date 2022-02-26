package com.mcssoft.racedaycompose.utility

sealed class DataResult<T> (
    val data: T? = null,
    val message: String? = null
) {
    // Parameter is nullable so we have the option to either pass Null, or some sort of result.
    class Success<T>(data: T) : DataResult<T>(data)

    class Error<T>(message: String, data: T? = null) : DataResult<T>(data, message)

    class Loading<T>(data: T? = null) : DataResult<T>(data)

}
/*
class Success<T>(data: T) : Resource<T>(data)
class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
class Loading<T>(data: T? = null) : Resource<T>(data)
*/
