package agency.five.codebase.android.weatherinfoapp.ui.favorites

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.ui.favorites.mapper.FavoritesMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
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
            .map { favoriteLocations ->
                favoriteLocations.map {
                    var temperature = 0
                    var iconId = ""
                    weatherInfoRepository.weatherInfo(it.lon.toDouble(), it.lat.toDouble()).map { weather ->
                        temperature = weather.currentWeather.currentTemperature
                        iconId = weather.currentWeather.iconId
                    }
                    FavoriteLocation(
                        it.location,
                        it.lon,
                        it.lat,
                        temperature,
                        iconId,
                        it.isFavorite,
                        it.isHome
                    )

                    }
            }
            .map(favoritesMapper::toFavoritesViewState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                FavoritesViewState(listOf())
            )

    fun toggleFavorite(location: String, lon: Double, lat: Double, iconId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.toggleFavorite(location, lat, lon, iconId)
        }
    }

    fun toggleHome(location: String, lon: Double, lat: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.addHomeLocation(location, lon, lat)
        }
    }
}
