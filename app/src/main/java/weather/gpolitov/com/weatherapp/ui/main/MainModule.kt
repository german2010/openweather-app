package weather.gpolitov.com.weatherapp.ui.main

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.ui.main.details.DetailsFragment
import weather.gpolitov.com.weatherapp.ui.main.details.DetailsModule
import weather.gpolitov.com.weatherapp.ui.main.favorite.FavoriteFragment
import weather.gpolitov.com.weatherapp.ui.main.favorite.FavoriteFragmentModule
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragment
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragmentModule
import weather.gpolitov.com.weatherapp.utils.SyncUtil
import weather.gpolitov.com.weatherapp.utils.Utils
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Singleton

@Module
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(MainFragmentModule::class))
    abstract fun mainFragmentInjector(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(FavoriteFragmentModule::class))
    abstract fun favoriteFragmentInjector(): FavoriteFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(DetailsModule::class))
    abstract fun detailsFragmentInjector(): DetailsFragment

    @Module
    companion object {
        @Provides
        @Singleton
        fun syncUtils(dataRepository: DataRepository,
                      baseSchedulerProvider: BaseSchedulerProvider,
                      utils: Utils): SyncUtil {
            return SyncUtil(dataRepository, baseSchedulerProvider, utils)
        }
    }
}