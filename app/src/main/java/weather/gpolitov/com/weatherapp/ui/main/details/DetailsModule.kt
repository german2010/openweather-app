package weather.gpolitov.com.weatherapp.ui.main.details

import dagger.Binds
import dagger.Module
import weather.gpolitov.com.weatherapp.di.FragmentScope


@Module
abstract class DetailsModule {

    @FragmentScope
    @Binds
    abstract fun createPresenter(detailsPresenter: DetailsPresenter): DetailsContract.Presenter
}