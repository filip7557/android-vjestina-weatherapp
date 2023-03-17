package agency.five.codebase.android.weatherinfoapp.data.di

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<WeatherInfoRepository> {
        WeatherInfoRepositoryImpl(
            weatherInfoService = get(),
            favoriteLocationDao = get(),
            homeLocationDao = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}
