package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbFavoriteLocation::class, DbHomeLocation::class], version = 1, exportSchema = false)
abstract class WeatherInfoAppDatabase : RoomDatabase() {
    abstract fun favoriteLocationDao(): FavoriteLocationDao
    abstract fun homeLocationDao(): HomeLocationDao
}
