package weather.gpolitov.com.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import weather.gpolitov.com.weatherapp.App
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.ui.main.MainActivity
import weather.gpolitov.com.weatherapp.ui.main.MainModule
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import weather.gpolitov.com.weatherapp.utils.schedulers.SchedulerProvider
import javax.inject.Singleton

@Module(includes = [(AndroidSupportInjectionModule::class)])
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideContext(app: App): Context

    @Singleton
    @Binds
    abstract fun provideBaseShedulerProvider(schedulerProvider: SchedulerProvider): BaseSchedulerProvider

    @Module
    companion object {
        @Provides
        @Singleton
        fun dataRepository(retrofit: Retrofit): DataRepository {
            return DataRepository(retrofit)
        }
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun mainActivityInjector(): MainActivity
}
