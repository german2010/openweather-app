package weather.gpolitov.com.weatherapp.data

import io.reactivex.Single
import retrofit2.Retrofit
import weather.gpolitov.com.weatherapp.data.local.LocalDataSource
import weather.gpolitov.com.weatherapp.data.remote.ApiService
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import javax.inject.Inject

open class DataRepository @Inject constructor(retrofit: Retrofit,
                                              private val localDataSource: LocalDataSource) {
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getWeatherByCity(city: String, units: String, appid: String): Single<WeatherResponse> {
        return apiService.getWeatherByCity(city, units, appid)
    }

    fun insertFavorite(vararg favorite: Favorite) {
        localDataSource.insertFavorite(*favorite)
    }

    fun getFavorites(): Single<List<Favorite>> {
        return localDataSource.getFavorites()
    }

    fun getCityList(): Single<List<String>> {
        return localDataSource.getCityList()
    }
}