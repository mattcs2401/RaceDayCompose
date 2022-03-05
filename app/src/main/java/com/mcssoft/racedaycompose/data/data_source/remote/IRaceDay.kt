package com.mcssoft.racedaycompose.data.data_source.remote

import com.mcssoft.racedaycompose.domain.dto.BaseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface IRaceDay {
    /* Notes:
       (1) The functions here must be suspend type functions. Seems to be Retrofit specific.
       (2) The two versions of getRaceDay() are simply for clarity.
    */

    /**
     * Get the tatts.com Api data.
     * @param date: The meeting date; as "YYYY/M(M)/D(D)".
     * @return A response anchored at BaseDto (refer ..\domain\dto\BaseDto).
     * @note Must be called from a coroutine or suspending function.
     */
    @GET("{date}")
    suspend fun getRaceDay(
        @Path("date") date: String
    ): BaseDto


    /**
     * Get the tatts.com Api data.
     * @param date: The meeting date; as "YYYY/M(M)/D(D)".
     * @param code: The meeting code; as "XX", e.g. "BR".
     * @return A response anchored at BaseDto (refer ..\domain\dto\BaseDto).
     * @note Must be called from a coroutine or suspending function.
     */
    @GET("{date}/{code}")
    suspend fun getRaceDay(
        @Path("date") date: String,
        @Path("code") code: String
    ): BaseDto

}