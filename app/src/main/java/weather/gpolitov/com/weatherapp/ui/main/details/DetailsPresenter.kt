package weather.gpolitov.com.weatherapp.ui.main.details

import io.reactivex.disposables.CompositeDisposable
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Inject

class DetailsPresenter @Inject internal constructor(private val dataRepository: DataRepository,
                                                    private val schedulerProvider: BaseSchedulerProvider)
    : DetailsContract.Presenter {


    var view: DetailsContract.View? = null

    private var compositeDisposable = CompositeDisposable()

    override fun takeView(view: DetailsContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
        compositeDisposable.clear()
    }

    override fun getDetailedWeatherByCity(name: String) {
        compositeDisposable.add(dataRepository.getDetailedWeatherByCity(name)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::onSuccess, this::onError))
    }

    fun onSuccess(favorite: Favorite) {
        view?.setData(favorite)
    }

    private fun onError(throwable: Throwable) {
        view?.showDBError()
    }
}