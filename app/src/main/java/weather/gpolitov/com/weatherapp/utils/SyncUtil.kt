package weather.gpolitov.com.weatherapp.utils

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import weather.gpolitov.com.weatherapp.BuildConfig
import weather.gpolitov.com.weatherapp.WEATHER_UNIT_SYSTEM
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import weather.gpolitov.com.weatherapp.utils.schedulers.BaseSchedulerProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncUtil @Inject internal constructor(private val dataRepository: DataRepository,
                                            private val schedulerProvider: BaseSchedulerProvider,
                                            private val utils: Utils) {

    private val compositeDisposable = CompositeDisposable()

    fun startSync() {
        compositeDisposable.add(dataRepository.getCityList()
                .flattenAsObservable { cities: List<String> -> cities }
                .flatMapSingle { city: String ->
                    dataRepository.getWeatherByCity(city, WEATHER_UNIT_SYSTEM, BuildConfig.appid)
                }
                .flatMap { t: WeatherResponse ->
                    val favorite = Favorite(null, t.city.name,
                            t.weatherList[0].main.temp.toString(), utils.getCurrentTime())
                    return@flatMap Observable.just(favorite)
                }
                .toList()
                .repeatWhen { t -> t.delay(20, TimeUnit.SECONDS) }
                .retryWhen { t -> t.delay(20, TimeUnit.SECONDS) }
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .subscribe(this::onSuccess, this::onError))
    }

    fun stopSync() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    private fun onSuccess(favorite: MutableList<Favorite>) {
        dataRepository.insertFavorite(*favorite.toTypedArray())
    }

    private fun onError(throwable: Throwable) {
    }
}