package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper

import agency.five.codebase.android.weatherinfoapp.model.*
import agency.five.codebase.android.weatherinfoapp.ui.component.*
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.WeatherInfoViewState

class WeatherInfoMapperImpl : WeatherInfoMapper {
    override fun toWeatherInfoViewState(
        weatherInfo: WeatherInfo
    ): WeatherInfoViewState {
        return WeatherInfoViewState(
            CurrentWeatherInfoCardViewState(weatherInfo.currentWeather),
            CurrentWeatherDetailsInfoCardViewState(weatherInfo.currentWeatherDetails),
            HourlyWeatherInfoCardViewState(weatherInfo.hourlyWeather),
            DailyWeatherInfoCardViewState(weatherInfo.dailyWeather),
            SunriseAndSunsetInfoCardViewState(weatherInfo.sunriseAndSunset)
        )
    }
}
