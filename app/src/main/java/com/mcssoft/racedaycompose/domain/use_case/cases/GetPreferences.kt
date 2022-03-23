package com.mcssoft.racedaycompose.domain.use_case.cases

import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.PreferenceType
import com.mcssoft.racedaycompose.utility.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// TODO - someway to signify which setting we are getting, maybe some sort of sealed class ?

class GetPreferences @Inject constructor (
    private val preferences: IPreferences
) {
    operator fun invoke(prefType: PreferenceType): Flow<DataResult<Any>> = flow {
        try {
            emit(DataResult.loading())

            val result = preferences.getPreference(prefType)

            emit(DataResult.success(result))

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

}