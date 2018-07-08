package weather.gpolitov.com.weatherapp.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import weather.gpolitov.com.weatherapp.model.Favorite

@Database(entities = [(Favorite::class)], version = 2)
abstract class AppDB : RoomDatabase() {

    abstract fun appDao(): AppDAO
}