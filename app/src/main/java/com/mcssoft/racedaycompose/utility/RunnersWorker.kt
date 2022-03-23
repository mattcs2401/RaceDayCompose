package com.mcssoft.racedaycompose.utility

import android.app.Application
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mcssoft.racedaycompose.RaceDayApp
import com.mcssoft.racedaycompose.data.data_source.database.RaceDayDb
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/****************************************************************
 * KEEP this as example of Hilt DI for unsupported types/classes.
 ****************************************************************/

class RunnerWorker(
    private val context: Context,
    private val params: WorkerParameters
): CoroutineWorker(context, params)
{
    /*
      Hilt DI Notes:
      https://developer.android.com/codelabs/android-hilt#10
      https://developer.android.com/training/dependency-injection/hilt-android#not-supported
    */
    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface IRemoteEntryPoint {
        fun remoteRepo(): IRemoteRepo
    }

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface IDbEntryPoint {
        fun dbRepo(): IDbRepo
    }

    private val iDbRepo = getDbRepo(context.applicationContext)

    private val iRemoteRepo = getRemoteRepo(context.applicationContext)


    override suspend fun doWork(): Result {

        val codes = iDbRepo.getMeetingCodes()

        val bp = codes

        return Result.success()
    }


    private fun getRemoteRepo(context: Context): IRemoteRepo {
        val hiltEntryPoint = EntryPointAccessors
            .fromApplication(context, IRemoteEntryPoint::class.java)
        return hiltEntryPoint.remoteRepo()
    }

    private fun getDbRepo(context: Context): IDbRepo {
        val hiltEntryPoint = EntryPointAccessors
            .fromApplication(context, IDbEntryPoint::class.java)
        return hiltEntryPoint.dbRepo()
    }
}