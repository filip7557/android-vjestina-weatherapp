package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.HourlyWeather
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class HourlyWeatherInfoCardViewState(
    val weathers: List<HourlyWeather>
)

@Composable
fun HourlyWeatherInfoCard(
    hourlyWeatherInfoCardViewState: HourlyWeatherInfoCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = AbsoluteRoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        LazyRow {
            items(count = hourlyWeatherInfoCardViewState.weathers.size) { index ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 7.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val hour = hourlyWeatherInfoCardViewState.weathers[index].hour
                    Text(
                        text = hour,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 5.dp)
                    )
                    AsyncImage(
                        model = "https://openweathermap.org/img/wn/${hourlyWeatherInfoCardViewState.weathers[index].iconId}@2x.png",
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(1.dp)
                    )
                    Text(
                        text = "${hourlyWeatherInfoCardViewState.weathers[index].temperature}°C",
                        fontSize = 15.sp,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 7.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = null
                        )
                        Text(
                            text = "${hourlyWeatherInfoCardViewState.weathers[index].humidity} %",
                            fontSize = 10.sp,
                        )
                    }
                }
            }
        }
    }
}

/*@Preview
@Composable
fun HourlyWeatherInfoCardPreview() {
    WeatherInfoTheme {
        HourlyWeatherInfoCard(
            hourlyWeatherInfoCardViewState = HourlyWeatherInfoCardViewState(getHourlyWeatherMock())
        )
    }
}*/
