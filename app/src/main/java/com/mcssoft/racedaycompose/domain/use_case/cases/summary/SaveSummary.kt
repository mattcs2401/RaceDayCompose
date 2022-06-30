package com.mcssoft.racedaycompose.domain.use_case.cases.summary

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSummary @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            //val summaries = iDbRepo.getSummary()

            emit(DataResult.success(""))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }
}