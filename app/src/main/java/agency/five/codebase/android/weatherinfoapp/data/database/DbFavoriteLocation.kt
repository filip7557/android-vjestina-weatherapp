package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteLocations")
data class DbFavoriteLocation(
    @PrimaryKey val location: String,
    @ColumnInfo(name = "lon") val lon: Float,
    @ColumnInfo(name = "lat") val lat: Float
)
