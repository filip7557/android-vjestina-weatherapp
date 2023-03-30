package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper.WeatherInfoMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WeatherInfoViewModel(
    private val weatherInfoRepository: WeatherInfoRepository,
    weatherInfoMapper: WeatherInfoMapper,
    val lon: Double,
    val lat: Double,
) : ViewModel() {

    private var isHomeEmpty: Boolean = false

    val weatherInfoViewState: StateFlow<WeatherInfoViewState> =
        weatherInfoRepository.weatherInfo(
            if (lon == 0.0) getHomeLocation().first else lon,
            if (lat == 0.0) getHomeLocation().second else lat,
        ).map(weatherInfoMapper::toWeatherInfoViewState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                WeatherInfoViewState.create()
            )

    fun toggleFavorite(location: String, iconId: String) {
        viewModelScope.launch {
            weatherInfoRepository.toggleFavorite(location, lat, lon, iconId)
        }
    }

    fun toggleHome(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.addHomeLocation(location, lon, lat)
        }
    }

    fun onHomeEmpty(): Boolean = isHomeEmpty

    private fun getHomeLocation() : Pair<Double, Double> {
        var pair = Pair(0.0, 0.0)
        var place = emptyList<FavoriteLocation>()
        viewModelScope.launch(Dispatchers.IO) {
            place = weatherInfoRepository.homePlace().first()
        }
        Thread.sleep(500L)
        if (place.isEmpty())
            isHomeEmpty = true
        else {
            pair = Pair(place.first().lon.toDouble(), place.first().lat.toDouble())
        }
        return pair
    }
}
