package agency.five.codebase.android.weatherinfoapp.navigation
import agency.five.codebase.android.weatherinfoapp.R

const val HOME_ROUTE = "Home"
const val FAVORITES_ROUTE = "Favorites"
const val SEARCH_ROUTE = "Search"

const val WEATHER_INFO_DETAILS_ROUTE = "WeatherInfoDetails"
const val WEATHER_LOCATION_KEY_LAT = "lat"
const val WEATHER_LOCATION_KEY_LON = "lon"
const val WEATHER_INFO_DETAILS_ROUTE_WITH_PARAMS = "$WEATHER_INFO_DETAILS_ROUTE/{$WEATHER_LOCATION_KEY_LAT}&{$WEATHER_LOCATION_KEY_LON}"

sealed class WeatherInfoAppDestination(
    open val route: String,
)

sealed class NavigationItem(
    override val route: String,
    val selectedIconId: Int,
    val unselectedIconId: Int,
    val labelId: Int,
) : WeatherInfoAppDestination(route) {

    object HomeDestination : NavigationItem(
        route = HOME_ROUTE,
        selectedIconId = R.drawable.ic_home_selected,
        unselectedIconId = R.drawable.ic_home_notselected,
        labelId = R.string.home,
    )

    object FavoritesDestination : NavigationItem(
        route = FAVORITES_ROUTE,
        selectedIconId = R.drawable.ic_favorite,
        unselectedIconId = R.drawable.ic_favorite_empty,
        labelId = R.string.favorites
    )

    object SearchDestination : NavigationItem(
        route = SEARCH_ROUTE,
        selectedIconId = R.drawable.ic_search_selected,
        unselectedIconId = R.drawable.ic_search_notselected,
        labelId = R.string.search
    )
}

object WeatherInfoDetailsDestination : WeatherInfoAppDestination(WEATHER_INFO_DETAILS_ROUTE_WITH_PARAMS) {
    fun createNavigation(lon: Float, lat: Float): String = "$WEATHER_INFO_DETAILS_ROUTE/${lat}&${lon}"
}
