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
): IRemoteRepo {

    override suspend fun getRaceDay(date: String, code: String): NetworkResponse<BaseDto> {
        return if(code == "") {
            apiCall { api.getRaceDay(date) }
        } else {
            apiCall { api.getRaceDay(date, code) }
        }
    }

    private inline fun <T> apiCall(apiCall: () -> Response<T>): NetworkResponse<T> {
        return try {
            NetworkResponse.success(apiCall.invoke())
        } catch(ex: Exception) {
            NetworkResponse.failure(ex)
        }
    }

}