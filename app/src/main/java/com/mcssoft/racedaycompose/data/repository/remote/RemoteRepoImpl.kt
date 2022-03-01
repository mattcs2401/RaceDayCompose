package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.data.data_source.remote.IRaceDay
import com.mcssoft.racedaycompose.domain.dto.BaseDto
import javax.inject.Inject

/**
 *
 */
class RemoteRepoImpl @Inject constructor(
    private val api: IRaceDay
): IRemoteRepo {

    override suspend fun getRaceDay(date: String): BaseDto {
        val base = api.getRaceDay(date)
        return base
    }


}