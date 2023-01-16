package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.di

import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.WeatherInfoViewModel
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper.WeatherInfoMapper
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper.WeatherInfoMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val weatherInfoModule = module {
    viewModel {
        (lon : String, lat : String) ->
        WeatherInfoViewModel(
            weatherInfoRepository = get(),
            weatherInfoMapper = get(),
            lon = lon,
            lat = lat,
        )
    }

    single<WeatherInfoMapper> { WeatherInfoMapperImpl() }
}
