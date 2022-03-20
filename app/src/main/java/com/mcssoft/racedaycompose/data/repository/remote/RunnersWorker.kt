package com.mcssoft.racedaycompose.data.repository.remote

import android.content.Context
import android.content.res.Resources
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.domain.dto.RaceDayDto
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.DbUtils
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.random.Random

/**
 * Class to process for the Runners.
 * @notes: Decided to use WorkManager as this should take longer. To get the Runner info, you have
 *         to hit the Api with the meeting code appended. So you are making an Api call for each
 *         meeting previously downloaded. The Meeting/Race info in that call will be the same as
 *         what already have, so will only be interested in the Runner info. This will be a lot of
 *         GSon data being processed.
 */
class RunnersWorker(
    private val context: Context,
    private val params: WorkerParameters
): CoroutineWorker(context, params) {

    @Inject lateinit var useCases: RaceDayUseCases

    private val resources: Resources = context.resources   // shortened form.

    override suspend fun doWork(): Result {
//        startForegroundService()

        // Get params.
        val codes = params.inputData
            .getStringArray(resources.getString(R.string.key_meeting_codes))
        val date = params.inputData
            .getString(resources.getString(R.string.key_meeting_date))

        var dataResMsg = ""

        codes?.forEach { code ->
            try {
                when (val result = localGetFromApi(date!!, code)) {
                    is DataResult.Loading -> {}
                    is DataResult.Error -> {
                        dataResMsg = result.message    // an exception occurred in GetFromApi().
                    }
                    is DataResult.Success -> {
                        val bp = "bp"
//                        localSaveFromApi(result.data as RaceDayDto)
                    }
                }
            } catch(ex: Exception) {
                // An exception local to this method.
                val exMsg = ex.localizedMessage ?: "[RunnersWorker] An unknown exception occurred."
                val data = workDataOf("runners_worker_exception" to exMsg)
                return Result.failure(data)
            }
        }
        return if(dataResMsg != "") {
            // An exception propagated from a previous call.
            Result.failure(workDataOf("runners_worker_exception" to dataResMsg))
        } else {
            Result.success()
        }
    }

    private fun localGetFromApi(date: String, code: String): DataResult<Any> {
        var dataResMsg = ""
        try {
            useCases.getFromApi(date, code).onEach { result ->
                when (result) {
                    is DataResult.Loading -> {}
                    is DataResult.Error -> {
                        dataResMsg = result.message    // an exception occurred in GetFromApi().
                    }
                    is DataResult.Success -> {
                        result.data?.let { dto -> localSaveFromApi(dto) }
                    }
                }
            }
        } catch(ex: Exception) {
            // An exception local to this method.
            val exMsg = ex.localizedMessage ?: "[RunnersWorker] An unknown exception occurred."
            return DataResult.Error(exMsg)
        }
        if(dataResMsg != "") {
            return DataResult.Error(dataResMsg)
        } else {
            return DataResult.Success("")
        }
    }

    private fun localSaveFromApi(dto: RaceDayDto): DataResult<Any> {
        var dataResMsg = ""
        try {
            useCases.saveFromApi(dto).onEach { result ->
                when(result) {
                    is DataResult.Loading -> { }
                    is DataResult.Error -> {
                        dataResMsg = result.message    // an exception occurred in SaveFromApi().
                    }
                    is DataResult.Success -> {

                    }
                }
            }
        } catch(ex: Exception) {
            // An exception local to this method.
            val exMsg = ex.localizedMessage ?: "[RunnersWorker] An unknown exception occurred."
            return DataResult.Error(exMsg)
        }
        if(dataResMsg != "") {
            return DataResult.Error(dataResMsg)
        } else {
            return DataResult.Success("")
        }
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, context.resources.getString(R.string.download_channel_id))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Runners Downloading")
                    .setContentText("Runners download in progress.")
                    .build()
            )
        )
    }
}