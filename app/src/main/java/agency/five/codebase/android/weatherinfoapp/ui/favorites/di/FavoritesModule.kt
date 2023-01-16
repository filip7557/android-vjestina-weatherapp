package agency.five.codebase.android.weatherinfoapp.ui.favorites.di

import agency.five.codebase.android.weatherinfoapp.ui.favorites.FavoritesViewModel
import agency.five.codebase.android.weatherinfoapp.ui.favorites.mapper.FavoritesMapper
import agency.five.codebase.android.weatherinfoapp.ui.favorites.mapper.FavoritesMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule = module {
    viewModel {
        FavoritesViewModel(
            weatherInfoRepository = get(),
            favoritesMapper = get(),
        )
    }

    single<FavoritesMapper> { FavoritesMapperImpl() }
}
