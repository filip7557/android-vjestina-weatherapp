package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.DailyWeather
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class DailyWeatherInfoCardViewState(
    val weathers: List<DailyWeather>
)

@Composable
fun DailyWeatherInfoCard(
    dailyWeatherInfoCardViewState: DailyWeatherInfoCardViewState,
    modifier: Modifier = Modifier
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
                verticalArrangement = Arrangement.spacedBy(17.dp),
                modifier = Modifier
                    .fillMaxWidth(0.47f)
                    .padding(8.dp),
            ) {
                for(day in dailyWeatherInfoCardViewState.weathers) {
                    Text(
                        text = day.day.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                        fontSize = 14.sp
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .padding(bottom = 0.dp)
            ) {
                for(weather in dailyWeatherInfoCardViewState.weathers) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                            ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = null,
                        )
                        Text(
                            text = "${weather.humidity}%${if(weather.humidity == 100) "" else "  "}",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(end = 10.dp)
                        )
                        AsyncImage(
                            model = "https://openweathermap.org/img/wn/${weather.iconId}@2x.png",
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(end = 5.dp)
                        )
                        Text(
                            text = "${weather.highTemperature}°C  ${weather.lowTemperature}°C",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

/*@Preview
@Composable
fun DailyWeatherInfoCardPreview() {
    WeatherInfoTheme {
        DailyWeatherInfoCard(
            dailyWeatherInfoCardViewState = DailyWeatherInfoCardViewState(getDailyWeatherMock())
        )
    }
}*/
