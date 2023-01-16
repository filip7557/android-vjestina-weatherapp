package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.CurrentWeatherDetails
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class CurrentWeatherDetailsInfoCardViewState(
    val weatherDetails: CurrentWeatherDetails
)

@Composable
fun CurrentWeatherDetailsInfoCard(
    currentWeatherDetailsInfoCardViewState: CurrentWeatherDetailsInfoCardViewState,
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
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .padding(bottom = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.ic_sun),
                    contentDescription = null,
                )
                Text(
                    text = "UV Index",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = currentWeatherDetailsInfoCardViewState.weatherDetails.uvIndex
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.ic_humidity_color),
                    contentDescription = null,
                )
                Text(
                    text = "Humidity",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 11.dp)
                )
                Text(
                    text = "${currentWeatherDetailsInfoCardViewState.weatherDetails.humidity}%"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 13.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.ic_wind),
                    contentDescription = null,
                )
                Text(
                    text = "Wind",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 11.dp)
                )
                Text(
                    text = "${currentWeatherDetailsInfoCardViewState.weatherDetails.wind} km/h"
                )
            }
        }
    }
}

@Preview
@Composable
fun CurrentWeatherDetailsInfoCardPreview() {
    WeatherInfoTheme {
        CurrentWeatherDetailsInfoCard(
            currentWeatherDetailsInfoCardViewState = CurrentWeatherDetailsInfoCardViewState(
                CurrentWeatherDetails(
                    uvIndex = "Low",
                    humidity = 89,
                    wind = 11,
                )
            )
        )
    }
}
