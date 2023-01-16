package agency.five.codebase.android.weatherinfoapp.ui.search.mapper

import agency.five.codebase.android.weatherinfoapp.ui.search.SearchViewState

class SearchMapperImpl : SearchMapper {
    override fun toSearchViewState(value: String): SearchViewState {
        return SearchViewState(value)
    }
}
