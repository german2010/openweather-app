package weather.gpolitov.com.weatherapp.ui.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.ui.main.favorite.FavoriteFragment
import weather.gpolitov.com.weatherapp.ui.main.favorite.FavoriteFragmentModule
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragment
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragmentModule

@Module
abstract class MainModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(MainFragmentModule::class))
    abstract fun mainFragmentInjector(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = arrayOf(FavoriteFragmentModule::class))
    abstract fun favoriteFragmentInjector(): FavoriteFragment
}