package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSettings @Inject constructor (
    private val preferences: IPreferences
) {
    operator fun invoke(fromDb: Boolean): Flow<DataResult<Any>> = flow {
        val result: DataResult<Any>

        try {
            preferences.saveFromDbPref(fromDb)

            emit(DataResult.Success(fromDb))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unknown error occurred."))
        }
    }

}