package weather.gpolitov.com.weatherapp.ui.main.favorite

import dagger.Binds
import dagger.Module
import weather.gpolitov.com.weatherapp.di.FragmentScope

@Module
abstract class FavoriteFragmentModule {

    @FragmentScope
    @Binds
    abstract fun createPresenter(favoriteFragmentPresenter: FavoriteFragmentPresenter): FavoriteFragmentContract.Presenter
}