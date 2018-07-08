package weather.gpolitov.com.weatherapp.data.local.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single
import weather.gpolitov.com.weatherapp.model.Favorite

@Dao
interface AppDAO {

    @Insert(onConflict = REPLACE)
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Single<List<Favorite>>
}