package agency.five.codebase.android.weatherinfoapp.data.network

import agency.five.codebase.android.weatherinfoapp.data.network.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.json.JSONArray

private const val BASE_URL = "https://api.openweathermap.org/data/3.0/onecall"
private const val BASE_URL_LOCATION_REVERSE = "https://api.openweathermap.org/geo/1.0/reverse"
private const val BASE_URL_LOCATION = "https://api.openweathermap.org/geo/1.0/direct"
private const val API_KEY = "5f2c5b65b64b29b045a9c119720479ea"

class WeatherInfoServiceImpl(private val client: HttpClient) : WeatherInfoService {
    override suspend fun fetchWeatherInfo(lon: Double, lat: Double): WeatherInfoResponse {
        return client.get("$BASE_URL?lat=$lat&lon=$lon&units=metric&exclude=daily,hourly,minutely&appid=$API_KEY").body()
    }

    override suspend fun fetchHourlyInfo(lon: Double, lat: Double): ApiHourly =
        client.get("$BASE_URL?lat=$lat&lon=$lon&units=metric&exclude=current,daily,minutely&appid=$API_KEY").body()

    override suspend fun fetchDailyInfo(lon: Double, lat: Double): ApiDaily =
        client.get("$BASE_URL?lat=$lat&lon=$lon&units=metric&exclude=current,hourly,minutely&appid=$API_KEY").body()

    override suspend fun fetchLocation(lon: Float, lat: Float): ApiLocation {
        val arr = JSONArray(client.get("$BASE_URL_LOCATION_REVERSE?lat=$lat&lon=$lon&limit=1&appid=$API_KEY").body<String>())
        val obj = arr.getJSONObject(0)
        return ApiLocation(obj.getString(("name"))
        )
    }

    override suspend fun fetchLonLat(location: String): ApiLonLat =
        client.get("$BASE_URL_LOCATION?q=location&limit=1&appid=$API_KEY").body()
}
