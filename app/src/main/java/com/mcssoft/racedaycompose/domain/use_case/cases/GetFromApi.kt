package com.mcssoft.racedaycompose.domain.use_case.cases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo

/**
 * Class to GET Meeting/Race details from the Api.
 * @param iRemoteRepo: Api access.
 */
class GetFromApi @Inject constructor(
    private val iRemoteRepo: IRemoteRepo
) {
    // TODO - add try/catch, some sort or error notification ?
    /**
     * @param mtgDate: The date to use in the Api Url.
     * @return A Flow of DataResult<RaceDayDto>.
     */
    operator fun invoke (mtgDate: String): Flow<DataResult<RaceDayDto>> = flow {
        try {
            emit(DataResult.Loading())

            // GET from the Api.
            val rdDto = iRemoteRepo.getRaceDay(mtgDate).RaceDay

            emit(DataResult.Success(rdDto))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }
    }

    /**
     * @param mtgDate: The date to use in the Api Url.
     * @param mtgCode: The meeting code to use in an extended version of the Api Url
     * @return A Flow of DataResult<RaceDayDto>.
     */
    operator fun invoke (mtgDate: String, mtgCode: String): Flow<DataResult<RaceDayDto>> = flow {
        try {
            emit(DataResult.Loading())

            // GET from the Api.
            val rdDto = iRemoteRepo.getRaceDay(mtgDate, mtgCode).RaceDay

            emit(DataResult.Success(rdDto))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }
    }
}
