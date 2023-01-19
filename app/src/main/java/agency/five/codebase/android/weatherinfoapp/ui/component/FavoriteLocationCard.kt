package agency.five.codebase.android.weatherinfoapp.ui.component

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class FavoriteLocationCardViewState(
    val favoriteLocation: FavoriteLocation
)

@Composable
fun FavoriteLocationCard(
    favoriteLocationCardViewState: FavoriteLocationCardViewState,
    modifier: Modifier = Modifier,
    onFavoriteClick: () -> Unit,
    onCardClick: () -> Unit,
    onHomeClick: () -> Unit,
) {
    Card(
        shape = AbsoluteRoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
            .clickable { onCardClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = favoriteLocationCardViewState.favoriteLocation.location,
                modifier = Modifier
                    .fillMaxWidth(0.20f)
                    .padding(start = 8.dp)
            )
            AsyncImage(
                model = "https://openweathermap.org/img/wn/${favoriteLocationCardViewState.favoriteLocation.iconId}@2x.png",
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 160.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "${favoriteLocationCardViewState.favoriteLocation.temperature}°C",
                modifier = Modifier
                    .fillMaxWidth(0.30f)
            )
            Icon(
                painterResource(id = if(favoriteLocationCardViewState.favoriteLocation.isHome) R.drawable.ic_home_selected else R.drawable.ic_home_notselected),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth(1f)
                    .clickable{ onHomeClick() }
            )
            Icon(
                painterResource(if(favoriteLocationCardViewState.favoriteLocation.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_empty),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .fillMaxWidth(1f)
                    .clickable { onFavoriteClick() }
            )
        }
    }
}

/*@Preview
@Composable
fun FavoriteLocationCardPreview() {
    WeatherInfoTheme {
        Column {
            FavoriteLocationCard(
                favoriteLocationCardViewState = FavoriteLocationCardViewState(
                    location = "Zagreb",
                    temperature = 10,
                    iconId = "10d",
                    isFavorite = true,
                ),
                onFavoriteClick = {},
                onCardClick = {}
            )
            FavoriteLocationCard(
                favoriteLocationCardViewState = FavoriteLocationCardViewState(
                    location = "Osijek",
                    temperature = 8,
                    iconId = "04d",
                    isFavorite = true,
                ),
                onFavoriteClick = {},
                onCardClick = {}
            )
        }
    }
}*/
