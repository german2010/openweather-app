package weather.gpolitov.com.weatherapp.ui.main.details

import weather.gpolitov.com.weatherapp.model.Favorite

interface DetailsContract {
    interface View {
        fun setData(favorite: Favorite)
        fun showDBError()
    }

    interface Presenter {
        fun takeView(view: DetailsContract.View)
        fun dropView()
        fun getDetailedWeatherByCity(name: String)
    }
}