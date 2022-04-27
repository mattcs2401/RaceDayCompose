package com.mcssoft.racedaycompose.data.repository.remote

// TODO - implement this instead of NetworkResponse ?
// From:
// https://proandroiddev.com/modeling-retrofit-responses-with-sealed-classes-and-coroutines-9d6302077dfe

sealed class NetworkResult<T : Any> {
    // Represents a network result that successfully received a response containing body data.
    class Success<T : Any>(val data: T) : NetworkResult<T>()

    // Represents a network result that successfully received a response containing an error message.
    class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()

    // Represents a network result that faced an unexpected exception before getting a response from
    // the network such as IOException and UnKnownHostException.
    class Exception<T : Any>(val ex: Throwable) : NetworkResult<T>()
}