package com.mcssoft.racedaycompose.domain.use_case.cases.summary

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.model.Summary
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSummaries @Inject constructor(
    private val iDbRepo: IDbRepo
) {
    operator fun invoke(): Flow<DataResult<List<Summary>>> = flow {
        try {
            emit(DataResult.loading())

            val summaries = iDbRepo.getSummaries()

            emit(DataResult.success(summaries))

        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }
}
