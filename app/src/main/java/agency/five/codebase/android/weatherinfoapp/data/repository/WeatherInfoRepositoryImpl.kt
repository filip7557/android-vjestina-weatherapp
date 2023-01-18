package agency.five.codebase.android.weatherinfoapp.data.repository

import agency.five.codebase.android.weatherinfoapp.data.database.DbFavoriteLocation
import agency.five.codebase.android.weatherinfoapp.data.database.DbHomeLocation
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
                val hourly = weatherInfoService.fetchHourlyInfo(lon, lat).hourly
                val daily = weatherInfoService.fetchDailyInfo(lon, lat).daily
                weatherInfoResponse.toWeatherInfo(
                    location = location.location,
                    isFavorite = favoriteLocations.any { it.location == location.location },
                    isHome = homePlace().any { it.location == location.location },
                    hourlyWeather = hourly,
                    dailyWeather = daily
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
                    isFavorite = true,
                    isHome = false,
                )
            }
        }.shareIn(
            scope = CoroutineScope(ioDispatcher),
            started = SharingStarted.WhileSubscribed(1000L),
            replay = 1
        )

    override fun homePlace(): List<FavoriteLocation> {
        var list = listOf(FavoriteLocation("", 0.0f, 0.0f, 0, "", true, isHome = true))
        weatherInfoDao.home().map {
                list = it.map { dbHomeLocation ->
                    FavoriteLocation(
                        location = dbHomeLocation.location,
                        lon = dbHomeLocation.lon.toFloat(),
                        lat = dbHomeLocation.lat.toFloat(),
                        temperature = 0,
                        iconId = "",
                        isFavorite = true,
                        isHome = true,
                    )
                }
            }
        return list
    }

    override suspend fun addHomeLocation(location: String, lon: Double, lat: Double) {
        weatherInfoDao.deleteHome()
        weatherInfoDao.insertHomeLocation(
            DbHomeLocation(
                location = location,
                lon = lon,
                lat = lat,
            )
        )
    }

    override suspend fun addLocationToFavorites(location: String, lat: Double, lon: Double) {
        weatherInfoDao.insertLocation(
            DbFavoriteLocation(
                location = location,
                lon = lon.toFloat(),
                lat = lat.toFloat()
            )
        )
    }

    override suspend fun removeLocationFromFavorites(location: String) {
        weatherInfoDao.delete(location)
    }

    override suspend fun toggleFavorite(location: String, lat: Double, lon: Double) {
        weatherInfoDao.favorites()
            .map {
                it.forEach { favLocation ->
                    if(favLocation.location == location)
                        removeLocationFromFavorites(location)
                    else
                        addLocationToFavorites(location, lat, lon)
                }
            }
    }
}
