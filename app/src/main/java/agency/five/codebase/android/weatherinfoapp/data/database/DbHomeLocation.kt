package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homeLocation")
data class DbHomeLocation(
    @PrimaryKey val location: String,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "lat") val lat: Double
)
