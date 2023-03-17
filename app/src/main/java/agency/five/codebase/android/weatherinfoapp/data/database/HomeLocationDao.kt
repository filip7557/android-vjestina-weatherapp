package agency.five.codebase.android.weatherinfoapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeLocationDao {
    @Query("SELECT * FROM homeLocation")
    fun home(): Flow<List<DbHomeLocation>>

    @Query("DELETE FROM homeLocation")
    suspend fun deleteHome()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeLocation(location: DbHomeLocation)
}
