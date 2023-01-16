package agency.five.codebase.android.weatherinfoapp.model

data class WeatherInfo(
    val currentWeather: CurrentWeather,
    val currentWeatherDetails: CurrentWeatherDetails,
    val dailyWeather: List<DailyWeather>,
    val hourlyWeather: List<HourlyWeather>,
    val sunriseAndSunset: SunriseAndSunset,
)
