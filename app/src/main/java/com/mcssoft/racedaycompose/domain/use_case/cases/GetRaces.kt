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
class GetRaces @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke (mId: Long): Flow<DataResult<List<Race>>> = flow {
        val result: DataResult<List<Race>>?

        try {
            emit(DataResult.Loading())

            // Get from the flow.
            result = DbUtils(iDbRepo).getRaces(mId)

            when {
                // An exception was thrown from DbUtils.
                result.failure -> {
                    emit(DataResult.Error(
                        result.exception ?:
                        Exception("[GetRaces] An unknown error or exception occurred.")))
                }
                // All good.
                else -> {
                    emit(DataResult.Success(result.data!!))
                }
            }
        } catch(exception: Exception) {
            emit(DataResult.Error(exception))
        }
    }

}
