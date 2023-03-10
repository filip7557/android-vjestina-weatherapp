package agency.five.codebase.android.weatherinfoapp.ui.search

import agency.five.codebase.android.weatherinfoapp.data.network.WeatherInfoService
import agency.five.codebase.android.weatherinfoapp.ui.search.mapper.SearchMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val weatherInfoService: WeatherInfoService,
    private val searchMapper: SearchMapper,
    private val isHomeEmpty: Boolean = false,
) : ViewModel() {

    private val viewState = MutableStateFlow(SearchViewState(""))

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchViewState: StateFlow<SearchViewState> =
        viewState.flatMapLatest {
            flow {
                emit(it)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            SearchViewState("")
        )

    fun onValueChange(value: String) {
        viewModelScope.launch {
            viewState.emit(
                searchMapper.toSearchViewState(value)
            )
        }
    }

    fun onDoneClick() : Pair<Double, Double> {
        var pair = Pair(0.0, 0.0)
        viewModelScope.launch(Dispatchers.IO) {
            pair = weatherInfoService.fetchLonLat(searchViewState.value.value).toLonLat()
        }
        Thread.sleep(500L)
        return pair
    }

    fun isHomeEmpty(): Boolean = isHomeEmpty
}
