package com.mcssoft.racedaycompose.data.data_source.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IRaceDay {
    /**
     * Get the tatts.com Api data.
     * @param date: The meeting date; as "YYYY/M(M)/D(D)".
     * @param code: The meeting code; as "XX", e.g. "BR" (optional depending on GET type).
     * @return A response anchored at BaseDto (refer ..\domain\dto\BaseDto).
     * @note Must be called from a coroutine or suspending function.
     */
    @GET("{date}/{code}")
    suspend fun getRaceDay(
        @Path("date") date: String,
        @Path("code") code: String = ""
    ): Response<BaseDto>

}