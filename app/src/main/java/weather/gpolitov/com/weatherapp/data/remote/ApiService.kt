package weather.gpolitov.com.weatherapp.data.remote

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import weather.gpolitov.com.weatherapp.model.WeatherResponse

interface ApiService {

//    @GET("forecat/?q=London,us&units=metric&appid=da3fa8d7ff1665ee1165bf0ae50444f3")
    @GET("forecast/?")
    fun getWeatherByCity(@Query("q") city: String,
                         @Query("units") units: String,
                         @Query("appid") appid: String): Single<WeatherResponse>
}