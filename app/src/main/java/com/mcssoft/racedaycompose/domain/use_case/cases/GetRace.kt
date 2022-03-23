package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get a list of Races from the database.
 * @param iDbRepo: Database access.
 */
class GetRace @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke (raceId: Long): Flow<DataResult<Race>> = flow {
        val result: DataResult<Race>?

        try {
            emit(DataResult.loading())

            // Get from the flow.
            result = DbUtils(iDbRepo).getRace(raceId)

            when {
                // An exception was thrown from DbUtils.
                result.failed -> {
                    emit(DataResult.failure(
                        result.exception ?:
                        Exception("[GetRaces] An unknown error or exception occurred.")))
                }
                // All good.
                else -> {
                    emit(DataResult.success(result.body))
                }
            }
        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
