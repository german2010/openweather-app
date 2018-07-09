package weather.gpolitov.com.weatherapp.ui.main.fragment

import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import weather.gpolitov.com.weatherapp.BuildConfig
import weather.gpolitov.com.weatherapp.WEATHER_UNIT_SYSTEM
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.di.FragmentScope
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import weather.gpolitov.com.weatherapp.utils.Utils
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import javax.inject.Inject

@FragmentScope
class MainFragmentPresenter @Inject internal constructor(private val dataRepository: DataRepository,
                                                         private val schedulerProvider: BaseSchedulerProvider,
                                                         private val utils: Utils)
    : MainFragmentContract.Presenter {

    var view: MainFragmentContract.View? = null

    var compositeDisposable = CompositeDisposable()

    lateinit var name: String
    lateinit var temp: String
    lateinit var description: String

    override fun getRequestById(city: String) {
        name = city.substring(0, 1).toUpperCase() + city.substring(1)

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
        temp = response.weatherList[0].main.temp.toString()
        description = response.weatherList[0].weather[0].description
    }

    private fun handleError(throwable: Throwable) {
        view?.hideProgressIndicator()
        when (throwable) {
            is HttpException -> {
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

    override fun saveToLocalStorage() {
        if (::name.isInitialized && ::temp.isInitialized) {
            dataRepository.insertFavorite(Favorite(null, name, temp, description, utils.getCurrentTime()))
        }
    }
}