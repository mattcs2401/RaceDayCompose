package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SavePreferences @Inject constructor (
    private val preferences: IPreferences
) {
    /**
     * Save the FromDb preference to the datastore.
     * @param value: The value to save.
     */
    operator fun invoke(prefType: PreferenceType, value: Boolean): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(prefType, value)

            emit(DataResult.success(value))

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    operator fun invoke(prefType: PreferenceType, value: Long): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            preferences.setPreference(prefType, value)

            emit(DataResult.success(value))

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}