package agency.five.codebase.android.weatherinfoapp.data.network

import agency.five.codebase.android.weatherinfoapp.data.network.model.*

interface WeatherInfoService {
    suspend fun fetchWeatherInfo(lon: Double, lat: Double): WeatherInfoResponse

    suspend fun fetchHourlyInfo(lon: Double, lat: Double): ApiHourly

    suspend fun fetchDailyInfo(lon: Double, lat: Double): ApiDaily

    suspend fun fetchLocation(lon: Float, lat: Float): ApiLocation

    suspend fun fetchLonLat(location: String): ApiLonLat
}
