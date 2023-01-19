package agency.five.codebase.android.weatherinfoapp.ui.search.di

import agency.five.codebase.android.weatherinfoapp.ui.search.SearchViewModel
import agency.five.codebase.android.weatherinfoapp.ui.search.mapper.SearchMapper
import agency.five.codebase.android.weatherinfoapp.ui.search.mapper.SearchMapperImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchModule = module {
    viewModel {
        (isHomeEmpty: Boolean) ->
        SearchViewModel(
            weatherInfoService = get(),
            searchMapper = get(),
            isHomeEmpty = isHomeEmpty
        )
    }

    single<SearchMapper> { SearchMapperImpl() }
}
