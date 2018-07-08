package weather.gpolitov.com.weatherapp.ui.main.favorite

import io.reactivex.disposables.CompositeDisposable
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Inject


@FragmentScope
class FavoriteFragmentPresenter @Inject internal constructor(private val dataRepository: DataRepository,
                                                             private val schedulerProvider: BaseSchedulerProvider)
    : FavoriteFragmentContract.Presenter {

    var view: FavoriteFragmentContract.View? = null

    var compositeDisposable = CompositeDisposable()

    override fun takeView(view: FavoriteFragmentContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
        compositeDisposable.clear()
    }

    override fun getFavorites() {
        compositeDisposable.add(dataRepository.getFavorites()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onSuccess))
    }

    private fun onSuccess(favorites: List<Favorite>) {
        view?.showFavorites(favorites)
    }
}