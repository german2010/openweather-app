package weather.gpolitov.com.weatherapp.ui.main.fragment

import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import weather.gpolitov.com.weatherapp.BuildConfig
import weather.gpolitov.com.weatherapp.WEATHER_UNIT_SYSTEM
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Inject

@FragmentScope
class MainFragmentPresenter @Inject internal constructor(private val dataRepository: DataRepository,
                                                         private val schedulerProvider: BaseSchedulerProvider)
    : MainFragmentContract.Presenter {

    var view: MainFragmentContract.View? = null

    var compositeDisposable = CompositeDisposable()

    override fun getRequestById(city: String) {
        view?.hideRecyclerView()
        compositeDisposable.add(dataRepository.getWeatherByCity(city, WEATHER_UNIT_SYSTEM, BuildConfig.appid)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { view?.showProgressIndicator() }
                .subscribe(this::handleResponse, this::handleError))
    }

    private fun handleResponse(response: WeatherResponse) {
        view?.hideProgressIndicator()
        view?.showRecyclerView()
        view?.showWeatherData(response)
    }

    private fun handleError(throwable: Throwable) {
        view?.hideProgressIndicator()
        when(throwable) {
            is HttpException ->  {
                view?.showRecyclerView()
                view?.showResponseError()
            }
            else -> {
                view?.showRecyclerView()
                view?.showNetworkError()
            }
        }
    }

    override fun takeView(view: MainFragmentContract.View) {
        this.view = view
    }

    override fun dropView() {
        view = null
        compositeDisposable.clear()
    }
}