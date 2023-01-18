package agency.five.codebase.android.weatherinfoapp.ui.weatherInfo

import agency.five.codebase.android.weatherinfoapp.ui.component.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherInfoRoute(
    viewModel: WeatherInfoViewModel
) {
    val viewState: WeatherInfoViewState by viewModel.weatherInfoViewState.collectAsState()

    WeatherInfoScreen(
        viewState,
        onFavoriteClick = {
            viewModel.toggleFavorite(it)
        },
        onHomeClick = {
            viewModel.toggleHome(it)
        }
    )
}

@Composable
fun WeatherInfoScreen(
    viewState: WeatherInfoViewState,
    onFavoriteClick: (String) -> Unit,
    onHomeClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState()),
    ) {
        CurrentWeatherInfoCard(currentWeatherInfoCardViewState = viewState.currentWeatherInfoCardViewState, onFavoriteClick = { onFavoriteClick(viewState.currentWeatherInfoCardViewState.weather.currentLocation) }, onHomeClick = {onHomeClick(viewState.currentWeatherInfoCardViewState.weather.currentLocation)})
        HourlyWeatherInfoCard(hourlyWeatherInfoCardViewState = viewState.hourlyWeatherInfoCardViewState, Modifier.height(145.dp))
        DailyWeatherInfoCard(dailyWeatherInfoCardViewState = viewState.dailyWeatherInfoCardViewState, Modifier.height(310.dp))
        CurrentWeatherDetailsInfoCard(currentWeatherDetailsInfoCardViewState = viewState.currentWeatherDetailsInfoCardViewState)
        SunriseAndSunsetInfoCard(sunriseAndSunsetInfoCardViewState = viewState.sunriseAndSunsetInfoCardViewState)
    }
}

/*@Preview
@Composable
fun WeatherInfoScreenPreview() {
    WeatherInfoTheme {
        WeatherInfoScreen(
            weatherInfoViewState,
            onFavoriteClick = {}
        )
    }
}*/
