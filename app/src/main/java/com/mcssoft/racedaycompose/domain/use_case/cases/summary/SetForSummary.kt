package com.mcssoft.racedaycompose.domain.use_case.cases.summary

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.mcssoft.racedaycompose.utility.DataResult
import com.mcssoft.racedaycompose.utility.SummaryWorker
import com.mcssoft.racedaycompose.utility.WorkerState
import kotlinx.coroutines.flow.*
import java.util.*

/**
 * Class to set a Summary object.
 */
class SetForSummary() {
    private lateinit var workManager: WorkManager

    operator fun invoke(raceId: Long,
                        runnerId: Long,
                        checked: Boolean,
                        context: Context
    ): Flow<DataResult<String>> = flow {

        workManager = WorkManager.getInstance(context)

        try {
            emit(DataResult.loading())

            val workData = workDataOf(
                "key_race_id" to raceId,
                "key_runner_id" to runnerId,
                "key_checked" to checked
            )

            val summaryWorker = OneTimeWorkRequestBuilder<SummaryWorker>()
                .addTag("SummaryWorker")
                .setInputData(workData)
                .build()
            workManager.enqueue(summaryWorker)

            observeSummaryWorker(summaryWorker.id).collect { result ->
                when (result) {
                    WorkerState.Scheduled -> {}
                    WorkerState.Cancelled -> {}
                    WorkerState.Failed -> {
                        throw Exception("Observe summaryWorker failure.")
                    }
                    WorkerState.Succeeded -> {
                        emit(DataResult.success(""))
                    }
                }
            }
        } catch (exception: Exception) {
            emit(DataResult.failure(exception))
        }
    }

    private fun observeSummaryWorker(workerId: UUID): Flow<WorkerState> {
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

    private fun mapWorkInfoStateToTaskState(state: WorkInfo.State): WorkerState = when (state) {
        WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING, WorkInfo.State.BLOCKED -> WorkerState.Scheduled
        WorkInfo.State.CANCELLED -> WorkerState.Cancelled
        WorkInfo.State.FAILED -> WorkerState.Failed
        WorkInfo.State.SUCCEEDED -> WorkerState.Succeeded
    }
}