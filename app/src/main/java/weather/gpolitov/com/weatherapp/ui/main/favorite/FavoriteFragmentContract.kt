package weather.gpolitov.com.weatherapp.ui.main.favorite

import weather.gpolitov.com.weatherapp.model.Favorite

interface FavoriteFragmentContract {
    interface Presenter {
        fun takeView(view: FavoriteFragmentContract.View)
        fun dropView()
        fun getFavorites()
    }

    interface View {
        fun showFavorites(favorites: List<Favorite>)
    }
}