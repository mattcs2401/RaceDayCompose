package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get a list of Meetings from the database.
 * @param iDbRepo: Database access.
 */
class GetMeeting @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(mId: Long): Flow<DataResult<Meeting>> = flow {
        val result: DataResult<Meeting>?

        try {
            emit(DataResult.loading())

            // Get from the flow.
            result = DbUtils(iDbRepo).getMeeting(mId)

            when {
                // An exception was thrown.
                result.failed -> {
                    emit(DataResult.failure(
                        result.exception ?:
                        Exception("[GetMeeting] An unknown error or exception occurred.")))
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
