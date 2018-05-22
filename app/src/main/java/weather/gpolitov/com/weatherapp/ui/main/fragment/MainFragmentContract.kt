package weather.gpolitov.com.weatherapp.ui.main.fragment

import retrofit2.Response
import weather.gpolitov.com.weatherapp.model.WeatherResponse

interface MainFragmentContract {
    interface Presenter {

        fun getRequestById(city: String)
        fun takeView(view: View)
        fun dropView()
    }

    interface View {
        fun showProgressIndicator()
        fun hideProgressIndicator()
        fun showRecyclerView()
        fun hideRecyclerView()
        fun showWeatherData(response: WeatherResponse)
        fun showResponseError()
        fun showNetworkError()
    }
}