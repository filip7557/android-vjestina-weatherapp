package agency.five.codebase.android.weatherinfoapp.model

data class CurrentWeather(
    val currentTemperature: Int,
    val currentLocation: String,
    val feelsLikeTemperature: Int,
    val iconId: String,
    val isFavorite: Boolean,
)
