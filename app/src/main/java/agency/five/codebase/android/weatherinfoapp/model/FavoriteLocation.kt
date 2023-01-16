package agency.five.codebase.android.weatherinfoapp.model

data class FavoriteLocation(
    val location: String,
    val lon: Float,
    val lat: Float,
    val temperature: Int,
    val iconId: String,
    val isFavorite: Boolean
)
