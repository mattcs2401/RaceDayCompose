package com.mcssoft.racedaycompose.data.data_source.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface IRaceDay {
    /* Note: The functions here must be suspend type functions. Seems to be Retrofit specific. */

    /**
     * Get the tatts.com api data.
     * @param date: The race date; as "YYYY/M(M)/D(D)".
     * @return A response anchored at BaseDto.
     * @note Must be called from a coroutine or suspending function.
     */
    @GET("{date}")
    suspend fun getRaceDay(@Path("date") date: String): BaseDto
}