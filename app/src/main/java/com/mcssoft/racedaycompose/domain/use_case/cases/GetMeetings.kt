package com.mcssoft.racedaycompose.domain.use_case.cases

import android.util.Log
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.utility.DataResult
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
        try {
            emit(DataResult.loading())

            var meetings = iDbRepo.getMeetings()

            // Filter if required.
            if(onlyAuNz) {
                // TBA - this could possibly still filter out something inadvertently.
                val value = meetings.filter { meeting ->
                    meeting.meetingCode.toCharArray()[1] != 'S' || meeting.meetingCode == "ZS"
                }
                meetings = value
            }

            emit(DataResult.success(meetings))

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}
