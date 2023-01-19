package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.CurrentWeather
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class CurrentWeatherInfoCardViewState(
    val weather: CurrentWeather
)

@Composable
fun CurrentWeatherInfoCard(
    currentWeatherInfoCardViewState: CurrentWeatherInfoCardViewState,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit,
    onHomeClick: () -> Unit
) {
    Card(
        shape = AbsoluteRoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(vertical = 40.dp, horizontal = 28.dp)
            ) {
                Text(
                    text = "${currentWeatherInfoCardViewState.weather.currentTemperature} °C",
                    fontSize = 25.sp,
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = currentWeatherInfoCardViewState.weather.currentLocation,
                        fontSize = 20.sp,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_location_pin),
                        contentDescription = null
                    )
                }
                Text(
                    text = "Feels like ${currentWeatherInfoCardViewState.weather.feelsLikeTemperature} °C",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${currentWeatherInfoCardViewState.weather.iconId}@2x.png",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .fillMaxWidth(0.75f),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(8.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Column {
                    Icon(
                        painter = painterResource(if (currentWeatherInfoCardViewState.weather.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_empty),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable(onClick = onFavoriteClick)
                    )
                    Icon(
                        painter = painterResource(if(currentWeatherInfoCardViewState.weather.isHome) R.drawable.ic_home_selected else R.drawable.ic_home_notselected),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable(onClick = onHomeClick)
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CurrentWeatherInfoItemPreview() {
    var selected by remember { mutableStateOf(false) }
    WeatherInfoTheme {
        CurrentWeatherInfoCard(
            currentWeatherInfoCardViewState = CurrentWeatherInfoCardViewState(
                CurrentWeather(
                    currentTemperature = 6,
                    currentLocation = "Našice",
                    feelsLikeTemperature = 2,
                    iconId = "10d",
                    isFavorite = selected,
                    isHome = true,
                )
            ),
            onFavoriteClick = { selected = selected.not()},
            onHomeClick = {}
        )
    }
}
