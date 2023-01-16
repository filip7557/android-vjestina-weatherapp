package agency.five.codebase.android.weatherinfoapp.ui.favorites

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val weatherInfoRepository: WeatherInfoRepository,
    favoritesMapper: FavoritesMapper,
) : ViewModel() {

    val favoritesViewState: StateFlow<FavoritesViewState> =
        weatherInfoRepository.favoritePlaces()
            .map(favoritesMapper::toFavoritesViewState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                FavoritesViewState(listOf())
            )

    fun toggleFavorite(location: String) {
        viewModelScope.launch {
            weatherInfoRepository.toggleFavorite(location)
        }
    }
}
