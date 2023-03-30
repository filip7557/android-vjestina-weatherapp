package agency.five.codebase.android.weatherinfoapp.data.repository

import agency.five.codebase.android.weatherinfoapp.data.database.DbFavoriteLocation
import agency.five.codebase.android.weatherinfoapp.data.database.DbHomeLocation
import agency.five.codebase.android.weatherinfoapp.data.database.FavoriteLocationDao
import agency.five.codebase.android.weatherinfoapp.data.database.HomeLocationDao
import agency.five.codebase.android.weatherinfoapp.data.network.WeatherInfoService
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.model.WeatherInfo
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

class WeatherInfoRepositoryImpl(
    private val weatherInfoService: WeatherInfoService,
    private val favoriteLocationDao: FavoriteLocationDao,
    private val homeLocationDao: HomeLocationDao,
    private val ioDispatcher: CoroutineDispatcher,
) : WeatherInfoRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun weatherInfo(lon: Double, lat: Double): Flow<WeatherInfo> = flow {
        emit(weatherInfoService.fetchWeatherInfo(lon, lat))
    }.flatMapLatest { weatherInfoResponse ->
        favoriteLocationDao.favorites()
            .map { favoriteLocations ->
                var location = ""
                if(lon != 0.0)
                    location = weatherInfoService.fetchLocation(weatherInfoResponse.lon.toFloat(), weatherInfoResponse.lat.toFloat()).location
                val hourly = weatherInfoService.fetchHourlyInfo(lon, lat).hourly
                val daily = weatherInfoService.fetchDailyInfo(lon, lat).daily
                weatherInfoResponse.toWeatherInfo(
                    location = location,
                    isFavorite = favoriteLocations.any { it.location == location },
                    isHome = homePlace().mapLatest {it}.first().any { it.location == location },
                    hourlyWeather = hourly,
                    dailyWeather = daily
                )
            }
    }.flowOn(ioDispatcher)

    override fun favoritePlaces(): Flow<List<FavoriteLocation>> =
        favoriteLocationDao.favorites().map {
            it.map { dbFavoriteLocation ->
                var isHome = false
                homePlace().map { homeLocations ->
                    isHome = homeLocations.first().isHome
                    Log.e("FAV LOC", "${dbFavoriteLocation.location} - $isHome")
                }
                FavoriteLocation(
                    location = dbFavoriteLocation.location,
                    lon = dbFavoriteLocation.lon,
                    lat = dbFavoriteLocation.lat,
                    temperature = 0,
                    iconId = dbFavoriteLocation.iconId,
                    isFavorite = true,
                    isHome = isHome,
                )
            }
        }.shareIn(
            scope = CoroutineScope(ioDispatcher),
            started = SharingStarted.WhileSubscribed(1000L),
            replay = 1
        )

    override fun homePlace(): Flow<List<FavoriteLocation>> =
        homeLocationDao.home().map { dbHome ->
            dbHome.map { dbHomeLocation ->
                    FavoriteLocation(
                        location = dbHomeLocation.location,
                        lon = dbHomeLocation.lon.toFloat(),
                        lat = dbHomeLocation.lat.toFloat(),
                        temperature = 0,
                        iconId = "",
                        isFavorite = false,
                        isHome = true,
                    )
                }
            }.shareIn(
            scope = CoroutineScope(ioDispatcher),
            started = SharingStarted.WhileSubscribed(1000L),
            replay = 1
            )

    override suspend fun addHomeLocation(location: String, lon: Double, lat: Double) {
        if(!homePlace().first().any { homePlace -> homePlace.location == location }) {
            homeLocationDao.deleteHome()
            homeLocationDao.insertHomeLocation(
                DbHomeLocation(
                    location = location,
                    lon = lon,
                    lat = lat
                )
            )
        }
    }

    override suspend fun addLocationToFavorites(location: String, lat: Double, lon: Double, iconId: String) {
        favoriteLocationDao.insertLocation(
            DbFavoriteLocation(
                location = location,
                lon = lon.toFloat(),
                lat = lat.toFloat(),
                iconId = iconId,
            )
        )
    }

    override suspend fun removeLocationFromFavorites(location: String) {
        favoriteLocationDao.delete(location)
    }

    override suspend fun toggleFavorite(location: String, lat: Double, lon: Double, iconId: String) {
        val favPlaces = favoritePlaces().first()
        if(favPlaces.any { favPlace -> favPlace.location == location })
            removeLocationFromFavorites(location)
        else
            addLocationToFavorites(location, lat, lon, iconId)
    }
}
