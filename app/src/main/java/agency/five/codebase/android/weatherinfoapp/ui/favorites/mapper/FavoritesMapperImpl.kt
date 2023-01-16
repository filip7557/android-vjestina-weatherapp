package agency.five.codebase.android.weatherinfoapp.ui.favorites.mapper

import agency.five.codebase.android.weatherinfoapp.model.FavoriteLocation
import agency.five.codebase.android.weatherinfoapp.ui.component.FavoriteLocationCardViewState
import agency.five.codebase.android.weatherinfoapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoritePlaces: List<FavoriteLocation>): FavoritesViewState {
        return FavoritesViewState(
            favoritePlaces.map {
                FavoriteLocationCardViewState(it)
            }
        )
    }
}
