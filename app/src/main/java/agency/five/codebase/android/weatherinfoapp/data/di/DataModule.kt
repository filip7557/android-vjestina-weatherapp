package agency.five.codebase.android.weatherinfoapp.data.di

import agency.five.codebase.android.weatherinfoapp.data.database.FavoriteLocationDao
import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepositoryImpl
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.di.weatherInfoModule
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val dataModule = module {
    single<WeatherInfoRepository> {
        WeatherInfoRepositoryImpl(
            weatherInfoService = get(),
            weatherInfoDao = get<FavoriteLocationDao>(),
            ioDispatcher = Dispatchers.IO
        )
    }
}
