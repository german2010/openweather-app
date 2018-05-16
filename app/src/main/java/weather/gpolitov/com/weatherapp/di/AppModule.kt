package weather.gpolitov.com.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [(AndroidSupportInjectionModule::class)])
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideContext(app: App): Context

    @Module
    companion object {
        @Provides
        @Singleton
        fun dataRepository(retrofit: Retrofit, context: Context): DataRepository {
            return DataRepository(retrofit, context)
        }
    }

    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    abstract fun mainActivityInjector(): MainActivity
}
