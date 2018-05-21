package weather.gpolitov.com.weatherapp.model

import com.squareup.moshi.Json

data class WeatherResponse(
        @Json(name = "list") val weatherList: List<WeatherList>
)

data class WeatherList(
        @Json(name = "main") val main: Main,
        @Json(name = "weather") val weather: List<Weather>,
        @Json(name = "dt_txt") val dateTime: String
)

data class Main(
        @Json(name = "temp") val temp: Double
)

data class Weather(
        @Json(name = "description") val description: String
)