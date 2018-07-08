package weather.gpolitov.com.weatherapp.data.local

import io.reactivex.Single
import weather.gpolitov.com.weatherapp.data.local.room.AppDAO
import weather.gpolitov.com.weatherapp.data.local.room.AppDB
import weather.gpolitov.com.weatherapp.model.Favorite
import javax.inject.Inject

class LocalDataSource @Inject constructor(db: AppDB) {
    private val dao: AppDAO = db.appDao()


    fun insertFavorite(favorite: Favorite) {
        dao.insertFavorite(favorite)
    }

    fun getFavorites(): Single<List<Favorite>> {
        return dao.getFavorites()
    }
}