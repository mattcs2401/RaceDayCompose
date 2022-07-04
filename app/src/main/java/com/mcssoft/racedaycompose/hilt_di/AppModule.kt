package com.mcssoft.racedaycompose.hilt_di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mcssoft.racedaycompose.R
import com.mcssoft.racedaycompose.data.data_source.database.RaceDayDb
import com.mcssoft.racedaycompose.data.data_source.remote.IRaceDay
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.preferences.IPreferences
import com.mcssoft.racedaycompose.data.repository.preferences.PreferencesImpl
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.data.repository.remote.RemoteRepoImpl
import com.mcssoft.racedaycompose.domain.use_case.RaceDayUseCases
import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupBaseFromApi
import com.mcssoft.racedaycompose.domain.use_case.cases.api.SetupRunnerFromApi
import com.mcssoft.racedaycompose.domain.use_case.cases.meetings.GetMeeting
import com.mcssoft.racedaycompose.domain.use_case.cases.meetings.GetMeetings
import com.mcssoft.racedaycompose.domain.use_case.cases.preferences.GetPreferences
import com.mcssoft.racedaycompose.domain.use_case.cases.preferences.SavePreferences
import com.mcssoft.racedaycompose.domain.use_case.cases.races.GetRace
import com.mcssoft.racedaycompose.domain.use_case.cases.races.GetRaces
import com.mcssoft.racedaycompose.domain.use_case.cases.runners.GetRunners
import com.mcssoft.racedaycompose.domain.use_case.cases.runners.SetRunnerChecked
import com.mcssoft.racedaycompose.domain.use_case.cases.summary.GetSummaries
import com.mcssoft.racedaycompose.domain.use_case.cases.summary.SetForSummary
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(app: Application): RaceDayDb {
        return Room.databaseBuilder(
            app,
            RaceDayDb::class.java,
            app.resources.getString(R.string.app_db_name)
        ).build()
    }

    @Provides
    fun provideRaceDayDao(db: RaceDayDb): IDbRepo {
        return db.getRaceDayDao()
    }

    @Provides
    fun provideApi(app: Application): IRaceDay {
//        val logging = HttpLoggingInterceptor()
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
        return Retrofit.Builder()
//            .client(client)
            .baseUrl(app.resources.getString(R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
            .create(IRaceDay::class.java)
    }

    @Provides
    fun provideRepository(api: IRaceDay): IRemoteRepo {
        return RemoteRepoImpl(api)
    }

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): IPreferences {
        return PreferencesImpl(context)
    }

    @Provides
    fun provideUseCases(
        remote: IRemoteRepo,
        local: IDbRepo,
        prefs: IPreferences
    ): RaceDayUseCases {
        return RaceDayUseCases(
            setupBaseFromApi = SetupBaseFromApi(remote, local),
            setupRunnerFromApi = SetupRunnerFromApi(),
            getMeeting = GetMeeting(local),
            getMeetings = GetMeetings(local),
            getRaces = GetRaces(local),
            getRace = GetRace(local),
            getRunners = GetRunners(local),
            getPreferences = GetPreferences(prefs),
            savePreferences = SavePreferences(prefs),
            setRunnerChecked = SetRunnerChecked(local),
            getSummaries = GetSummaries(local),
            setForSummary = SetForSummary()
        )
    }

}