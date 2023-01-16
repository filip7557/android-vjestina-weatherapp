package agency.five.codebase.android.weatherinfoapp.ui.search.mapper

import agency.five.codebase.android.weatherinfoapp.ui.search.SearchViewState

interface SearchMapper {
    fun toSearchViewState(value: String): SearchViewState
}
