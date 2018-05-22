package weather.gpolitov.com.weatherapp.data

import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import weather.gpolitov.com.weatherapp.data.remote.ApiService
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import javax.inject.Inject

open class DataRepository @Inject constructor(retrofit: Retrofit) {
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getWeatherByCity(city: String, units: String, appid: String): Single<WeatherResponse> {
        return apiService.getWeatherByCity(city, units, appid)
    }
}