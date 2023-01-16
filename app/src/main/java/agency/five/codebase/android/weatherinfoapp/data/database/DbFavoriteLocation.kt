package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteLocations")
data class DbFavoriteLocation(
    @PrimaryKey
    val location: String,
    val lon: Float,
    val lat: Float
)
