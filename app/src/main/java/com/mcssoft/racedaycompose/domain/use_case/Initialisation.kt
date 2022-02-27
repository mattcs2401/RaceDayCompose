package com.mcssoft.racedaycompose.domain.use_case

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.dto.MeetingDto
import com.mcssoft.racedaycompose.domain.dto.RaceDto
import com.mcssoft.racedaycompose.domain.dto.toMeeting
import com.mcssoft.racedaycompose.domain.dto.toRace
import com.mcssoft.racedaycompose.domain.model.Meeting
import com.mcssoft.racedaycompose.domain.model.Race
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Class that does a clean install/re-install. Basically:
 * 1. Delete anything already in the database.
 * 2. GET (Meeting/Race) from the Api.
 * 3. Re-populate the database.
 * @note: The single action "Initialisation" via the invoke() operator actually comprises several
 *        elements, because we are also using the database as a cache, and need to establish that
 *        from the Api result. Subsequent calls for UI screen data will get from the database.
 */
class Initialisation @Inject constructor(
    private val iRemoteRepo: IRemoteRepo,
    private val iDbRepo: IDbRepo
) {
    // TODO - add try/catch, some sort or error notification ?
    /**
     * @param mtgDate: The date to use in the Api Url.
     * @param useFromDb: A flag to indicate get from existing database data, or reload completely.
     * @param mtgType: The MeetingType to filter on, defaults to "R" (but "G and "T" also possible).
     * @return A Flow of DataResult (basically just signify; loading, success or failure).
     */
    operator fun invoke (mtgDate: String, useFromDb: Boolean, mtgType: String = "R"): Flow<DataResult<String>> = flow {
        try {
            emit(DataResult.Loading())

            val count = meetingCount()
            if((useFromDb && count < 1) || (!useFromDb)) {
                DbUtils(iRemoteRepo, iDbRepo).refresh(mtgDate, mtgType)
            }

            emit(DataResult.Success(""))

        } catch(ex: Exception) {
            emit(DataResult.Error(ex.localizedMessage ?: "An unexpected error occurred."))
        }
    }

    private suspend fun meetingCount(): Int {
        return iDbRepo.meetingCount()
    }
}
