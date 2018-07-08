package weather.gpolitov.com.weatherapp.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorites",
        indices = [Index(value = ["name"], unique = true)])
data class Favorite(@PrimaryKey(autoGenerate = true) var id: Long?,
                    @ColumnInfo(name = "name") var name: String,
                    @ColumnInfo(name = "temp") var temp: String
) {
    constructor() : this(null, "", "")
}