package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto

interface IRemoteRepo {

    suspend fun getRaceDay(date: String): BaseDto
}