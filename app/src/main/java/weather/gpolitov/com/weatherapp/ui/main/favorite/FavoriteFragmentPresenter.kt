package weather.gpolitov.com.weatherapp.ui.main.favorite

import io.reactivex.disposables.CompositeDisposable
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.utils.Utils
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Inject


@FragmentScope
class FavoriteFragmentPresenter @Inject internal constructor(private val dataRepository: DataRepository,
                                                             private val schedulerProvider: BaseSchedulerProvider,
                                                             private val utils: Utils)
    : FavoriteFragmentContract.Presenter {

    var view: FavoriteFragmentContract.View? = null

    private var compositeDisposable = CompositeDisposable()

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
                .doOnSubscribe { view?.showProgressIndicator() }
                .subscribe(this::onSuccess, this::onError))
    }

    private fun onError(throwable: Throwable) {
        view?.hideProgressIndicator()
        view?.showDBError()
    }

    private fun onSuccess(favorites: List<Favorite>) {
        view?.hideProgressIndicator()
        view?.showFavorites(favorites, utils.isNetworkConnected())
    }
}