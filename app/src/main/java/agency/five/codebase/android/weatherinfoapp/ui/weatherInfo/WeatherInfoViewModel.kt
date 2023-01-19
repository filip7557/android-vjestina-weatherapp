package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper.WeatherInfoMapper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherInfoViewModel(
    private val weatherInfoRepository: WeatherInfoRepository,
    weatherInfoMapper: WeatherInfoMapper,
    val lon: Double,
    val lat: Double,
) : ViewModel() {

    var isHomeEmpty: Boolean = false

    val weatherInfoViewState: StateFlow<WeatherInfoViewState> =
        weatherInfoRepository.weatherInfo(
            if(lon == 0.0) getHomeLocation().first else lon,
            if(lat == 0.0) getHomeLocation().second else lat,
        ).map(weatherInfoMapper::toWeatherInfoViewState)
            .stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                WeatherInfoViewState.create()
            )

    fun toggleFavorite(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.toggleFavorite(location, lat, lon)
        }
    }

    fun toggleHome(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherInfoRepository.addHomeLocation(location, lon, lat)
        }
    }

    fun onHomeEmpty(): Boolean = isHomeEmpty

    private fun getHomeLocation() : Pair<Double, Double> {

        val home = weatherInfoRepository.homePlace()

        if(home.first().location == "") {
            isHomeEmpty = true
            return Pair(0.0, 0.0)
        }

        return Pair(home.first().lon.toDouble(), home.first().lat.toDouble())
    }
}
