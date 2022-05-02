package com.mcssoft.racedaycompose.data.repository.remote

import com.mcssoft.racedaycompose.data.data_source.remote.IRaceDay
import com.mcssoft.racedaycompose.domain.dto.BaseDto
import retrofit2.Response
import javax.inject.Inject

/**
 * Class to implement the method that gets from the Api.
 */
class RemoteRepoImpl @Inject constructor(
    private val api: IRaceDay
) : IRemoteRepo {

    override suspend fun getRaceDay(date: String, code: String): NetworkResponse<BaseDto> {
        return try {
            val result = api.getRaceDay(date, code)
            when {
                result.isSuccessful -> {
                    NetworkResponse.success(result)
                }
                else -> {
                    NetworkResponse.error(result.message())
                }
            }
        } catch (ex: Exception) {
            NetworkResponse.exception(ex)
        }
    }

}