package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.utility.Constants.NO_MEETINGS
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get a list of Meetings from the database.
 * @param iDbRepo: Database access.
 */
class GetMeetings @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     * @param mtgType: The MeetingType to filter on, defaults to "R" ATT (but "G and "T" are also
     *                 possible).
     * @return A DataResult of Success(List<Meeting>) or Error(message).
     */
    operator fun invoke (): Flow<DataResult<List<Meeting>>> = flow {
        val result: DataResult<List<Meeting>>?

        try {
            emit(DataResult.Loading())

            // Get from the flow.
            result = DbUtils(iDbRepo).getMeetings()

            when {
                // No exception, but no Meetings populated either.
                result.message == NO_MEETINGS -> {
                    emit(DataResult.Error(result.message))
                }
                // An exception was thrown.
                result.message != "" && result.message != NO_MEETINGS -> {
                    emit(DataResult.Error(result.message))
                }
                // All good.
                else -> {
                    emit(DataResult.Success(result.data!!))
                }
            }
        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "GetMeetings.invoke: An unexpected error occurred."))
        }
    }

}
