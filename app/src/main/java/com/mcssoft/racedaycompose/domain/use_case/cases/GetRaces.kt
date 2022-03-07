package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.Constants
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

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
                // No exception, but no Meetings populated either.
                result.message == Constants.NO_RACES -> {
                    emit(DataResult.Error(result.message))
                }
                // An exception was thrown.
                result.message != "" && result.message != Constants.NO_RACES -> {
                    emit(DataResult.Error(result.message))
                }
                // All good.
                else -> {
                    emit(DataResult.Success(result.data!!))
                }
            }
        } catch(ex: Exception) {

        }
    }

}
