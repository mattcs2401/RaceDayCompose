package com.mcssoft.racedaycompose.domain.use_case.cases.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.RunnersWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class SetupRunnerFromApi @Inject constructor(
    private val remote: IRemoteRepo,
    private val local: IDbRepo,
    private val context: Context
) {

    operator fun invoke (date: String): Flow<DataResult<String>> = flow {

        try {
            emit(DataResult.loading())

            val workManager = WorkManager.getInstance(context)
            val workData = workDataOf("key" to date)
            val runnersWorker = OneTimeWorkRequestBuilder<RunnersWorker>()
                .addTag("RunnersWorker")
                .setInputData(workData)
                .build()
            workManager.enqueue(runnersWorker)

            observeRunnerWorker(workManager, runnersWorker.id)

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    private fun observeRunnerWorker(workManager: WorkManager, id: UUID) {
//        val workInfo = workManager.getWorkInfoByIdLiveData(id).observe(this) { workInfo ->
//            when (workInfo.state) {
//                WorkInfo.State.SUCCEEDED -> {
//                    workManager.cancelWorkById(id)
//                }
//                WorkInfo.State.FAILED -> {
//                    Log.d("TAG", "[WorkInfo.State.FAILED] Meeting")
//                    workManager.cancelWorkById(id)
////                    workerFailure(MEETING, workInfo.outputData)
//                }
//                else -> { }
//            }
//        }
    }

    // Define the observer function
    private fun workInfosObserver(): Observer {
        return Observer { listOfWorkInfo, _ ->

//            // Note that these next few lines grab a single WorkInfo if it exists
//            // This code could be in a Transformation in the ViewModel; they are included here
//            // so that the entire process of displaying a WorkInfo is in one location.
//
//            // If there are no matching work info, do nothing
//            if (listOfWorkInfo.isNullOrEmpty()) {
//                return@Observer
//            }
//
//            // We only care about the one output status.
//            // Every continuation has only one worker tagged TAG_OUTPUT
//            val workInfo = listOfWorkInfo[0]
//
//            if (workInfo.state.isFinished) {
//                showWorkFinished()
//            } else {
//                showWorkInProgress()
//            }
        }
    }

}