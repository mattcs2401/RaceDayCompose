package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveSettings @Inject constructor (
    private val preferences: IPreferences
) {
    /**
     * Save the FromDb preference to the datastore.
     * @param fromDb: The value to save.
     */
    operator fun invoke(fromDb: Boolean): Flow<DataResult<Any>> = flow {
        try {
            preferences.saveFromDbPref(fromDb)

            emit(DataResult.Success(fromDb))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unknown error occurred."))
        }
    }

}