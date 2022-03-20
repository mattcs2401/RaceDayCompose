package com.mcssoft.racedaycompose.domain.use_case.cases

import android.util.Log
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo

/**
 * Class to GET details from the Api.
 * @param iRemoteRepo: Api access.
 */
class GetFromApi @Inject constructor(
    private val iRemoteRepo: IRemoteRepo
) {
    /**
     * @param mtgDate: The date to use in the Api Url.
     * @param mtgCode: The Meeting's code (optional Api Url mod).
     * @return A Flow of DataResult<RaceDayDto>.
     * @notes If Meeting code is not used, then all Meetings are returned in the RaceDayDto details,
     *        else, just the one Meeting.
     */
    operator fun invoke (mtgDate: String, mtgCode: String = ""): Flow<DataResult<RaceDayDto>> = flow {
        Log.d("TAG","GetFromApi.invoke()")
        emit(DataResult.Loading())

        // GET from the Api (as NetworkResponse<BasteDto>).
        val result = iRemoteRepo.getRaceDay(mtgDate, mtgCode)

        if(result.failed) {
            emit(DataResult.Error(
                result.exception?.localizedMessage ?:
                "[GetFromApi] Exception: An unexpected error occurred."))
        } else if(!result.isSuccessful) {
            emit(DataResult.Error(
                "[GetFromApi] Not an exception, but an unexpected error occurred."))
        } else {
            emit(DataResult.Success(result.body.RaceDay))
        }
    }

}
