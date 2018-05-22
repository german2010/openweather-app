package weather.gpolitov.com.weatherapp.ui.main.fragment

import dagger.Binds
import dagger.Module
import weather.gpolitov.com.weatherapp.di.FragmentScope

@Module
abstract class MainFragmentModule {

    @FragmentScope
    @Binds
    abstract fun createPresenter(aboutProductPresenter: MainFragmentPresenter): MainFragmentContract.Presenter
}