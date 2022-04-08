package com.mcssoft.racedaycompose.domain.use_case.cases.api

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asFlow
import androidx.work.*
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.RunnerTaskState
import com.mcssoft.racedaycompose.utility.RunnersWorker
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

class SetupRunnerFromApi {

    private lateinit var workManager: WorkManager

    operator fun invoke (date: String, context: Context): Flow<DataResult<String>> = flow {

        workManager = WorkManager.getInstance(context)

        try {
            emit(DataResult.loading())

            val workData = workDataOf("key_date" to date)
            val runnersWorker = OneTimeWorkRequestBuilder<RunnersWorker>()
                .addTag("RunnersWorker")
                .setInputData(workData)
                .build()
            workManager.enqueue(runnersWorker)

            observeRunnerWorker(runnersWorker.id).collect { result ->
                when(result) {
                    RunnerTaskState.Scheduled -> {}
                    RunnerTaskState.Cancelled -> {}
                    RunnerTaskState.Failed -> {}
                    RunnerTaskState.Completed -> {}
                }
            }

        } catch(exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    private fun observeRunnerWorker(workerId: UUID): Flow<RunnerTaskState> {
        return workManager.getWorkInfoByIdLiveData(workerId)
            .asFlow()
            .map { workInfo ->
                mapWorkInfoStateToTaskState(workInfo.state)
            }
            .transformWhile { taskState ->
                emit(taskState)
                // This is to terminate this flow when terminal state is arrived.
                !taskState.isTerminalState
            }.distinctUntilChanged()
    }

    private fun mapWorkInfoStateToTaskState(state: WorkInfo.State): RunnerTaskState = when (state) {
        WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING, WorkInfo.State.BLOCKED -> RunnerTaskState.Scheduled
        WorkInfo.State.CANCELLED -> RunnerTaskState.Cancelled
        WorkInfo.State.FAILED -> RunnerTaskState.Failed
        WorkInfo.State.SUCCEEDED -> RunnerTaskState.Completed
    }
}