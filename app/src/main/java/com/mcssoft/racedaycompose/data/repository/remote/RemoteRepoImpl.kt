package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.data.data_source.remote.IRaceDay
import com.mcssoft.racedaycompose.domain.dto.BaseDto
import javax.inject.Inject

/**
 * Class to implement the methods that get from the Api.
 */
class RemoteRepoImpl @Inject constructor(
    private val api: IRaceDay
): IRemoteRepo {

    override suspend fun getRaceDay(date: String): BaseDto {
        return api.getRaceDay(date)
    }

    override suspend fun getRaceDay(date: String, code: String): BaseDto {
        return api.getRaceDay(date, code)
    }

}