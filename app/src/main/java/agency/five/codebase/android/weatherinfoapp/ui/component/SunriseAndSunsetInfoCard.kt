package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.SunriseAndSunset
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class SunriseAndSunsetInfoCardViewState(
    val sunInfo: SunriseAndSunset
)

@Composable
fun SunriseAndSunsetInfoCard(
    sunriseAndSunsetInfoCardViewState: SunriseAndSunsetInfoCardViewState,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = AbsoluteRoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.49f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.sunrise),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = sunriseAndSunsetInfoCardViewState.sunInfo.sunrise,
                )
                Image(
                    painterResource(id = R.drawable.ic_sunrise),
                    contentDescription = null,
                )
            }
            Icon(
                painterResource(id = R.drawable.ic_separator),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.sunrise),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(5.dp)
                )
                Text(
                    text = sunriseAndSunsetInfoCardViewState.sunInfo.sunset,
                )
                Image(
                    painterResource(id = R.drawable.ic_sunset),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
fun SunriseAndSunsetInfoCardPreview() {
    WeatherInfoTheme {
        SunriseAndSunsetInfoCard(
            sunriseAndSunsetInfoCardViewState = SunriseAndSunsetInfoCardViewState(
                SunriseAndSunset(
                    sunrise = "07:23",
                    sunset = "16:30"
                )
            )
        )
    }
}
