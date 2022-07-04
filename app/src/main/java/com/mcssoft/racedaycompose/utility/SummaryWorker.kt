package com.mcssoft.racedaycompose.utility

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

class SummaryWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface IEntryPoint {
        fun getDbRepo(): IDbRepo
    }

    private val entryPoint = EntryPointAccessors.fromApplication(context, IEntryPoint::class.java)
    val iDbRepo = entryPoint.getDbRepo()

    override suspend fun doWork(): Result {
        val raceId = inputData.getString("key_race_id")
        val runnerId = inputData.getString("key_runner_id")
        val checked = inputData.getBoolean("key_checked", false)

        try {



        } catch (exception: Exception) {
            Log.d("TAG", "[SummaryWorker.doWork] exception.")
            val output = workDataOf("key_exception" to exception.localizedMessage)
            return Result.failure(output)
        }
        Log.d("TAG", "[SummaryWorker.doWork] ending.")
        return Result.success()
    }

    private fun checkRunnerIdExists(runnerId: Long): Boolean {


        return true
    }

    private fun createSummaryEntry() {

    }



}
/*
    Logic:
    ------
    -> Check if a Summary has the passed in 'runnerId'
       -> No
            -> Then create a new Summary.
       -> Yes
            -> Is the passed in checked state == false ?
               -> Yes
                  -> Remove Summary
 */