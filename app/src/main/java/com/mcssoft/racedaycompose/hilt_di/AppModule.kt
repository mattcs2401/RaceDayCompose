package com.mcssoft.racedaycompose.hilt_di

import android.app.Application
import androidx.room.Room
import com.mcssoft.racedaycompose.data.data_source.database.RaceDayDb
import com.mcssoft.racedaycompose.data.data_source.remote.IRaceDay
import com.mcssoft.racedaycompose.data.repository.database.IDbRepo
import com.mcssoft.racedaycompose.data.repository.remote.IRemoteRepo
import com.mcssoft.racedaycompose.data.repository.remote.RemoteRepoImpl
import com.mcssoft.racedaycompose.domain.use_case.*
import com.mcssoft.racedaycompose.domain.use_case.cases.*
import com.mcssoft.racedaycompose.utility.Constants
import com.mcssoft.racedaycompose.utility.DateUtils
import com.mcssoft.racedaycompose.utility.DbUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): RaceDayDb {
        return Room.databaseBuilder(
            app,
            RaceDayDb::class.java,
            "race_day_compose.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRaceDayDao(db: RaceDayDb): IDbRepo { //RaceDayDao {
        return db.getRaceDayDao()
    }

    @Provides
    @Singleton
    fun provideApi(): IRaceDay {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRaceDay::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: IRaceDay): IRemoteRepo {
        return RemoteRepoImpl(api)
    }

    @Singleton
    @Provides
    fun provideRaceDayUtilities(): DateUtils {
        return DateUtils()
    }

    @Provides
    @Singleton
    fun provideUseCases(remote: IRemoteRepo, local: IDbRepo): RaceDayUseCases {
        return RaceDayUseCases(
            getFromApi = GetFromApi(remote),
            saveFromApi = SaveFromApi(local),
            getMeeting = GetMeeting(local),
            getMeetings = GetMeetings(local),
            getRaces = GetRaces(local),
            getRunners = GetRunners(local)
        )
    }

    @Provides
    @Singleton
    fun provideDbUtils(local: IDbRepo): DbUtils {
        return DbUtils(local)
    }

}