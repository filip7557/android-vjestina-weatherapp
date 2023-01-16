package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteLocationDao {

    @Query("SELECT * FROM favoriteLocations")
    fun favorites(): Flow<List<DbFavoriteLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: DbFavoriteLocation)

    @Query("DELETE FROM favoriteLocations WHERE location = :location")
    suspend fun delete(location: String)
}
