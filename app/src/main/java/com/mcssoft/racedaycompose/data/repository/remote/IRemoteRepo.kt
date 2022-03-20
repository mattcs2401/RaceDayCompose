package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto

interface IRemoteRepo {

// Original:
//    suspend fun getRaceDay(date: String, code: String): BaseDto

    suspend fun getRaceDay(date: String, code: String): NetworkResponse<BaseDto>

}