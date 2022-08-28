package com.mcssoft.racedaycompose.utility.network

import kotlinx.coroutines.flow.Flow

//https://www.youtube.com/watch?v=TzV0oCRDNfM

interface IConnObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}