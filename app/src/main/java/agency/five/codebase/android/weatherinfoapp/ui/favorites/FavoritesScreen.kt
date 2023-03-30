package agency.five.codebase.android.weatherinfoapp.ui.favorites

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.ui.component.FavoriteLocationCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavoritesRoute(
    viewModel: FavoritesViewModel,
    onCardClick: (Float, Float) -> Unit
) {
    val viewState: FavoritesViewState by viewModel.favoritesViewState.collectAsState()

    FavoritesScreen(
        viewState,
        onFavoriteClick = { location, lon, lat, iconId ->
            viewModel.toggleFavorite(location, lon, lat, iconId)
        },
        onCardClick,
        onHomeClick = { location, lon, lat ->
            viewModel.toggleHome(location, lon, lat)
        }
    )
}

@Composable
fun FavoritesScreen(
    viewState: FavoritesViewState,
    onFavoriteClick: (String, Double, Double, String) -> Unit,
    onCardClick: (Float, Float) -> Unit,
    onHomeClick: (String, Double, Double) -> Unit,
) {
    Column {
        Text(
            text = stringResource(id = R.string.favorites),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(8.dp)
        )
        LazyColumn {
            items(viewState.favoritesPlaces.size) { index ->
                FavoriteLocationCard(
                    favoriteLocationCardViewState = viewState.favoritesPlaces[index],
                    onFavoriteClick = { onFavoriteClick(viewState.favoritesPlaces[index].favoriteLocation.location, viewState.favoritesPlaces[index].favoriteLocation.lon.toDouble(), viewState.favoritesPlaces[index].favoriteLocation.lat.toDouble(), viewState.favoritesPlaces[index].favoriteLocation.iconId) },
                    onCardClick = { onCardClick(
                        viewState.favoritesPlaces[index].favoriteLocation.lat,
                        viewState.favoritesPlaces[index].favoriteLocation.lon )
                    },
                    onHomeClick = {
                        onHomeClick(
                            viewState.favoritesPlaces[index].favoriteLocation.location,
                            viewState.favoritesPlaces[index].favoriteLocation.lon.toDouble(),
                            viewState.favoritesPlaces[index].favoriteLocation.lat.toDouble()
                        )
                    }
                )
            }
        }
    }
}
