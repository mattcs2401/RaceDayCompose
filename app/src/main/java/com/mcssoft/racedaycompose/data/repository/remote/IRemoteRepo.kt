package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto

interface IRemoteRepo {

    suspend fun getRaceDay(date: String, code: String): NetworkResponse<BaseDto>

}