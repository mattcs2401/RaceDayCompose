package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMeetings @Inject constructor(
    private val iRepo: IDbRepo
) {

    operator fun invoke (): Flow<DataResult<List<Meeting>>> = flow {
        try {
            emit(DataResult.Loading())

            val meetings = iRepo.getMeetings()

            emit(DataResult.Success(meetings))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?:
            "[GetMeetings]: An unexpected error occurred."))
        }
    }

}