package agency.five.codebase.android.weatherinfoapp.data.repository

import agency.five.codebase.android.weatherinfoapp.data.database.DbFavoriteLocation
import agency.five.codebase.android.weatherinfoapp.data.database.FavoriteLocationDao
import agency.five.codebase.android.weatherinfoapp.data.network.WeatherInfoService
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.model.WeatherInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class WeatherInfoRepositoryImpl(
    private val weatherInfoService: WeatherInfoService,
    private val weatherInfoDao: FavoriteLocationDao,
    private val ioDispatcher: CoroutineDispatcher,
) : WeatherInfoRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun weatherInfo(lon: Double, lat: Double): Flow<WeatherInfo> = flow {
        emit(weatherInfoService.fetchWeatherInfo(lon, lat))
    }.flatMapLatest { weatherInfoResponse ->
        weatherInfoDao.favorites()
            .map { favoriteLocations ->
                val location = weatherInfoService.fetchLocation(weatherInfoResponse.lon.toFloat(), weatherInfoResponse.lat.toFloat())
                weatherInfoResponse.toWeatherInfo(
                    location.location,
                    favoriteLocations.any { it.location == location.location },
                    weatherInfoService.fetchHourlyInfo(lon, lat).hourly,
                    weatherInfoService.fetchDailyInfo(lon, lat).daily
                )
            }
    }.flowOn(ioDispatcher)

    override fun favoritePlaces(): Flow<List<FavoriteLocation>> =
        weatherInfoDao.favorites().map {
            it.map { dbFavoriteLocation ->
                FavoriteLocation(
                    location = dbFavoriteLocation.location,
                    lon = dbFavoriteLocation.lon,
                    lat = dbFavoriteLocation.lat,
                    temperature = 0,
                    iconId = "",
                    isFavorite = true
                )
            }
        }.shareIn(
            scope = CoroutineScope(ioDispatcher),
            started = SharingStarted.Eagerly,
            replay = 1
        )

    override suspend fun addLocationToFavorites(location: String) {
        val lonLat = weatherInfoService.fetchLonLat(location)
        weatherInfoDao.insertLocation(
            DbFavoriteLocation(
                location = location,
                lon = lonLat.lon.toFloat(),
                lat = lonLat.lat.toFloat()
            )
        )
    }

    override suspend fun removeLocationFromFavorites(location: String) {
        weatherInfoDao.delete(location)
    }

    override suspend fun toggleFavorite(location: String) {
        weatherInfoDao.favorites()
            .map {
                it.forEach { favLocation ->
                    if(favLocation.location == location)
                        removeLocationFromFavorites(location)
                    else
                        addLocationToFavorites(location)
                }
            }
    }
}
