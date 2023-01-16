package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper

import agency.five.codebase.android.weatherinfoapp.model.*
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.WeatherInfoViewState

interface WeatherInfoMapper {
    fun toWeatherInfoViewState(
        weatherInfo: WeatherInfo
    ) : WeatherInfoViewState
}
