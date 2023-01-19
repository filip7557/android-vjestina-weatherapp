package agency.five.codebase.android.weatherinfoapp.ui.favorites

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.ui.component.FavoriteLocationCard
import agency.five.codebase.android.weatherinfoapp.ui.component.FavoriteLocationCardViewState
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
        onFavoriteClick = { location, lon, lat ->
            viewModel.toggleFavorite(location, lon, lat)
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
    onFavoriteClick: (String, Double, Double) -> Unit,
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
                    onFavoriteClick = { onFavoriteClick(viewState.favoritesPlaces[index].favoriteLocation.location, viewState.favoritesPlaces[index].favoriteLocation.lon.toDouble(), viewState.favoritesPlaces[index].favoriteLocation.lat.toDouble()) },
                    onCardClick = { onCardClick(
                        viewState.favoritesPlaces[index].favoriteLocation.lon,
                        viewState.favoritesPlaces[index].favoriteLocation.lat )
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

@Preview
@Composable
fun FavoritesScreenPreview() {
    WeatherInfoTheme {
        FavoritesScreen(
            viewState = FavoritesViewState(
                listOf(
                    FavoriteLocationCardViewState(
                        FavoriteLocation(
                            "Našice",
                            45.49f,
                            18.09f,
                            4,
                            "10d",
                            true,
                            true,
                        )
                    ),
                    FavoriteLocationCardViewState(
                        FavoriteLocation(
                            "Osijek",
                            45.56f,
                            18.70f,
                            6,
                            "04d",
                            true,
                            true,
                        )
                    ),
                    FavoriteLocationCardViewState(
                        FavoriteLocation(
                            "Osijek",
                            45.56f,
                            18.70f,
                            6,
                            "04d",
                            true,
                            true,
                        )
                    )
                )
            ),
            onCardClick = { lon, lat ->

            },
            onFavoriteClick = { location, lon, lat ->

            },
            onHomeClick = { location, lon, lat ->

            }
        )
    }
}
