package com.mcssoft.racedaycompose.domain.use_case

import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshMeetings@Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo
) {
    operator fun invoke (mtgDate: String, mtgType: String = "R"): Flow<DataResult<String>> = flow {
        try {
            emit(DataResult.Loading())

            DbUtils(iRemoteRepo, iDbRepo).refresh(mtgDate, mtgType)

            emit(DataResult.Success(""))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }
    }
}