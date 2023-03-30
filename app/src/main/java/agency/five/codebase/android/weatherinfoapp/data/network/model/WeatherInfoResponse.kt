package agency.five.codebase.android.weatherinfoapp.data.network.model

import agency.five.codebase.android.weatherinfoapp.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DateFormat.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

var globalTimeZoneOffset: Int = 0

@Serializable
data class WeatherInfoResponse(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timezone_offset")
    val timeZoneOffset: Int,
    @SerialName("current")
    val current: ApiCurrent,
) {
    fun toWeatherInfo(location: String, isFavorite: Boolean, isHome: Boolean, hourlyWeather: List<ApiHourlyWeather>, dailyWeather: List<ApiDailyWeather>): WeatherInfo {
        val uv = when (current.uvIndex.toInt()) {
            0 or 1 or 2 -> "Low"
            3 or 4 or 5 -> "Moderate"
            6 or 7 -> "High"
            8 or 9 or 10 -> "Very high"
            else -> "Low"
        }
        //globalTimeZoneOffset = timeZoneOffset - timeZoneOffset
        val timeSunrise = Date((current.sunrise+ globalTimeZoneOffset)*1000)
        val timeSunset = Date((current.sunset+ globalTimeZoneOffset)*1000)
        val format = getTimeInstance()
        val sunrise = format.format(timeSunrise)
        val sunset = format.format(timeSunset)
        return WeatherInfo(
            currentWeather = CurrentWeather(current.temperature.toInt(), location, current.feelsLike.toInt(), current.weather[0].iconId, isFavorite, isHome),
            currentWeatherDetails = CurrentWeatherDetails(uv, current.humidity, current.windSpeed.toInt()),
            dailyWeather = dailyWeather.map { it.toDailyWeather() },
            hourlyWeather = hourlyWeather.map { it.toHourlyWeather() },
            sunriseAndSunset = SunriseAndSunset(sunrise, sunset)
        )
    }
}

@Serializable
data class ApiCurrent(
    @SerialName("temp")
    val temperature: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("uvi")
    val uvIndex: Double,
    @SerialName("wind_speed")
    val windSpeed: Double,
    @SerialName("weather")
    val weather: List<ApiWeather>,
    @SerialName("sunrise")
    val sunrise: Long,
    @SerialName("sunset")
    val sunset: Long,
)

@Serializable
data class ApiHourly(
    @SerialName("timezone_offset")
    val timeZoneOffset: Int,
    @SerialName("hourly")
    val hourly: List<ApiHourlyWeather>,
)

@Serializable
data class ApiDaily(
    @SerialName("daily")
    val daily: List<ApiDailyWeather>
)

@Serializable
data class ApiTemperature(
    @SerialName("max")
    val highTemperature: Double,
    @SerialName("min")
    val lowTemperature: Double,
)

@Serializable
data class ApiDailyWeather(
    @SerialName("dt")
    val day: Long,
    @SerialName("temp")
    val temperature: ApiTemperature,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("weather")
    val weather: List<ApiWeather>,
) {
    fun toDailyWeather(): DailyWeather {
        val date = Date(day*1000)
        val cal = Calendar.getInstance()
        cal.time = date
        val daysArray = arrayOf( DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY )

        return DailyWeather(
            highTemperature = temperature.highTemperature.toInt(),
            lowTemperature = temperature.lowTemperature.toInt(),
            humidity = humidity,
            iconId = weather[0].iconId,
            day = daysArray[cal.get(Calendar.DAY_OF_WEEK)-1]
        )
    }
}

@Serializable
data class ApiHourlyWeather(
    @SerialName("dt")
    val time: Long,
    @SerialName("temp")
    val temperature: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("weather")
    val weather: List<ApiWeather>,
) {
    fun toHourlyWeather(): HourlyWeather {
        val date = Date((time+ globalTimeZoneOffset)*1000)
        val format = SimpleDateFormat("HH:mm")
        return HourlyWeather(
            temperature = temperature.toInt(),
            humidity = humidity,
            iconId = weather[0].iconId,
            hour = format.format(date)
        )
    }
}

@Serializable
data class ApiWeather(
    @SerialName("icon")
    val iconId: String
)

@Serializable
data class ApiLocation(
    @SerialName("name")
    val location: String,
)

@Serializable
data class ApiLonLat(
    @SerialName("lon")
    val lon: Double,
    @SerialName("lat")
    val lat: Double,
) {
    fun toLonLat(): Pair<Double, Double> {
        return Pair(lon, lat)
    }
}
