package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo

import agency.five.codebase.android.weatherinfoapp.data.repository.WeatherInfoRepository
import agency.five.codebase.android.weatherinfoapp.fusedLocationClient
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.mapper.WeatherInfoMapper
import android.annotation.SuppressLint
import android.location.Location
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

    val weatherInfoViewState: StateFlow<WeatherInfoViewState> =
        weatherInfoRepository.weatherInfo(
            if(lon == 0.0) getLocation().first else lon,
            if(lat == 0.0) getLocation().second else lat,
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

    @SuppressLint("MissingPermission")
    private fun getLocation() : Pair<Double, Double> {
        var lon = 18.09
        var lat = 45.49
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat = location!!.latitude
                lon = location.longitude
            }
        return Pair(lon, lat)
    }
}
