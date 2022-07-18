package com.mcssoft.racedaycompose.domain.use_case.cases.splash

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckPrePopulate @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    /**
     *
     */
    operator fun invoke(): Flow<DataResult<Boolean>> = flow {
        try {
            emit(DataResult.loading())

            val trainers = iDbRepo.getTrainerCount()

            emit(DataResult.success(trainers > 0))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}