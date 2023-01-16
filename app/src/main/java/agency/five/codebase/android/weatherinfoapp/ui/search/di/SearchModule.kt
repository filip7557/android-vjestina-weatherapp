package agency.five.codebase.android.weatherinfoapp.ui.search.di

import agency.five.codebase.android.weatherinfoapp.ui.search.SearchViewModel
import agency.five.codebase.android.weatherinfoapp.ui.search.mapper.SearchMapper
import agency.five.codebase.android.weatherinfoapp.ui.search.mapper.SearchMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel {
        SearchViewModel(
            weatherInfoService = get(),
            searchMapper = get(),
        )
    }

    single<SearchMapper> { SearchMapperImpl() }
}
