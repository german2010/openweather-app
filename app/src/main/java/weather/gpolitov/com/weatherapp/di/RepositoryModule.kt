package weather.gpolitov.com.weatherapp.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.data.local.LocalDataSource
import weather.gpolitov.com.weatherapp.data.local.room.AppDB
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppDb(context: Context): AppDB {
        return Room.databaseBuilder(context, AppDB::class.java, "AppDb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun dataRepository(retrofit: Retrofit, localDataSource: LocalDataSource): DataRepository {
        return DataRepository(retrofit, localDataSource)
    }

    @Provides
    @Singleton
    fun localDataSource(appDB: AppDB): LocalDataSource {
        return LocalDataSource(appDB)
    }
}