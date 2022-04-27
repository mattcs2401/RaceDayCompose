package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SetRunnerChecked @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(runnerId: Long, checked: Boolean): Flow<DataResult<String>> = flow {
        try {
//            emit(DataResult.loading())

            iDbRepo.setRunnerChecked(runnerId, checked)

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}