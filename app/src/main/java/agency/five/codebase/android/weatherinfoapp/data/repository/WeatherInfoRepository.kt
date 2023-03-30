package agency.five.codebase.android.weatherinfoapp.data.repository

import agency.five.codebase.android.weatherinfoapp.model.*
import kotlinx.coroutines.flow.Flow

interface WeatherInfoRepository {

    fun weatherInfo(lon: Double, lat: Double): Flow<WeatherInfo>

    fun favoritePlaces(): Flow<List<FavoriteLocation>>

    fun homePlace(): Flow<List<FavoriteLocation>>

    suspend fun addHomeLocation(location: String, lon: Double, lat: Double)

    suspend fun addLocationToFavorites(location: String, lat: Double, lon: Double, iconId: String)

    suspend fun removeLocationFromFavorites(location: String)

    suspend fun toggleFavorite(location: String, lat: Double, lon: Double, iconId: String)

}
