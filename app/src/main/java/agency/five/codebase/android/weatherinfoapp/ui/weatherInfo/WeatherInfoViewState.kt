package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo

import agency.five.codebase.android.weatherinfoapp.model.CurrentWeather
import agency.five.codebase.android.weatherinfoapp.model.CurrentWeatherDetails
import agency.five.codebase.android.weatherinfoapp.model.SunriseAndSunset
import agency.five.codebase.android.weatherinfoapp.ui.component.*

data class WeatherInfoViewState(
    val currentWeatherInfoCardViewState: CurrentWeatherInfoCardViewState,
    val currentWeatherDetailsInfoCardViewState: CurrentWeatherDetailsInfoCardViewState,
    val hourlyWeatherInfoCardViewState: HourlyWeatherInfoCardViewState,
    val dailyWeatherInfoCardViewState: DailyWeatherInfoCardViewState,
    val sunriseAndSunsetInfoCardViewState: SunriseAndSunsetInfoCardViewState
) {
    companion object Empty {
        fun create() = WeatherInfoViewState(
            CurrentWeatherInfoCardViewState(
                CurrentWeather(
                    0,
                    "",
                    0,
                    "",
                    false,
                    false,
                )
            ),
            CurrentWeatherDetailsInfoCardViewState(
                CurrentWeatherDetails(
                    "",
                    0,
                    0
                )
            ),
            HourlyWeatherInfoCardViewState(listOf()),
            DailyWeatherInfoCardViewState(listOf()),
            SunriseAndSunsetInfoCardViewState(
                SunriseAndSunset("", "")
            )
        )
    }
}
