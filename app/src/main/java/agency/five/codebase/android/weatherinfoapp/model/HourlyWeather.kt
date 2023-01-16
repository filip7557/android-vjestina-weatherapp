package agency.five.codebase.android.weatherinfoapp.model

data class HourlyWeather(
    val temperature: Int,
    val humidity: Int,
    val iconId: String,
    val hour: String,
)
