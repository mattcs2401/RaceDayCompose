package com.mcssoft.racedaycompose.domain.use_case.cases

import android.util.Log
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
class GetMeetings @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke (onlyAuNz: Boolean): Flow<DataResult<List<Meeting>>> = flow {
        Log.d("TAG","GetMeetings.invoke()")
        val result: DataResult<List<Meeting>>?

        try {
            emit(DataResult.loading())

            // Get from the flow.
            result = DbUtils(iDbRepo).getMeetings()

            // Filter if required.
            if(onlyAuNz) {
                // TBA - this possibly could still filter out something inadvertently.
                val value = result.data?.filter { meeting ->
                    meeting.meetingCode.toCharArray()[1] != 'S' || meeting.meetingCode == "ZS"
                }
                result.data = value
            }

            when {
                // An exception was thrown from DbUtils.
                result.failed -> {
                    emit(DataResult.failure(result.exception ?:
                    Exception("[GetMeetings] An unknown error or exception occurred.")))
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
