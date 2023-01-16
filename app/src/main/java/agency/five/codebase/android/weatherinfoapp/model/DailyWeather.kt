package agency.five.codebase.android.weatherinfoapp.model

import java.time.DayOfWeek

data class DailyWeather(
    val highTemperature: Int,
    val lowTemperature: Int,
    val humidity: Int,
    val iconId: String,
    val day: DayOfWeek
)
