package agency.five.codebase.android.weatherinfoapp.ui.main

import agency.five.codebase.android.weatherinfoapp.R
import agency.five.codebase.android.weatherinfoapp.navigation.*
import agency.five.codebase.android.weatherinfoapp.ui.favorites.FavoritesRoute
import agency.five.codebase.android.weatherinfoapp.ui.favorites.FavoritesViewModel
import agency.five.codebase.android.weatherinfoapp.ui.search.SearchRoute
import agency.five.codebase.android.weatherinfoapp.ui.search.SearchViewModel
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.WeatherInfoRoute
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.WeatherInfoViewModel
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val weatherInfoViewModel = getViewModel<WeatherInfoViewModel>(parameters = {
        parametersOf(0.0, 0.0) })
    val favoritesViewModel = getViewModel<FavoritesViewModel>()
    val searchViewModel = getViewModel<SearchViewModel>()

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
                BottomNavigationBar(
                    destinations = listOf(
                        NavigationItem.HomeDestination,
                        NavigationItem.SearchDestination,
                        NavigationItem.FavoritesDestination,
                    ),
                    onNavigateToDestination = {
                        navController.navigate(it.route) {
                            popUpTo(HOME_ROUTE) {
                                if(it.route == HOME_ROUTE)
                                    inclusive = true
                            }
                        }
                    },
                    currentDestination = navBackStackEntry?.destination
                )
        }
    ) { padding ->
        Surface(
            color = if(isSystemInDarkTheme()) Color.Black else Color(0xFFF3F0F0),
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.HomeDestination.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.HomeDestination.route) {
                    WeatherInfoRoute(weatherInfoViewModel)
                }
                composable(NavigationItem.FavoritesDestination.route) {
                    FavoritesRoute(
                        onCardClick = { lon, lat ->
                            navController.navigate(WeatherInfoDetailsDestination.createNavigation(lon, lat))
                        },
                        viewModel = favoritesViewModel,
                    )
                }
                composable(NavigationItem.SearchDestination.route) {
                    SearchRoute(
                        viewModel = searchViewModel,
                        onDoneClick = {
                            val cords = searchViewModel.onDoneClick()
                            Log.d("NAV ROUTE", WeatherInfoDetailsDestination.createNavigation(cords.first.toFloat(), cords.second.toFloat()))
                            navController.navigate(WeatherInfoDetailsDestination.createNavigation(cords.first.toFloat(), cords.second.toFloat()))
                        }
                    )
                }
                composable(
                    route = WeatherInfoDetailsDestination.route,
                    arguments = listOf(navArgument(WEATHER_LOCATION_KEY_LAT) { type = NavType.FloatType }, navArgument(WEATHER_LOCATION_KEY_LON) { type = NavType.FloatType }),
                ) {
                    val lon = it.arguments?.getFloat(WEATHER_LOCATION_KEY_LON)?.toDouble()
                    val lat = it.arguments?.getFloat(WEATHER_LOCATION_KEY_LAT)?.toDouble()
                    val viewModel = getViewModel<WeatherInfoViewModel>(parameters = {
                        parametersOf(lat, lon)
                    })
                    WeatherInfoRoute(viewModel)
                }
            }
        }
    }
}

@Composable
private fun TopBar(
){
    TopAppBar(
        backgroundColor = if(isSystemInDarkTheme()) androidx.compose.material3.MaterialTheme.colorScheme.surface else Color.White,
        modifier = Modifier
            .fillMaxHeight(0.07f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 25.sp
            )
        }
    }
}

@Composable
private fun BottomNavigationBar(
    destinations: List<NavigationItem>,
    onNavigateToDestination: (NavigationItem) -> Unit,
    currentDestination: NavDestination?
) {
    BottomNavigation(
        backgroundColor = if(isSystemInDarkTheme()) androidx.compose.material3.MaterialTheme.colorScheme.surface else Color.White
    ) {
        destinations.forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination?.route == destination.route,
                icon = {
                    Icon(
                        painter = painterResource(id = if (currentDestination?.route == destination.route) destination.selectedIconId else destination.unselectedIconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = destination.labelId),
                        style = MaterialTheme.typography.button
                    )
                },
                onClick = { onNavigateToDestination(destination) })
        }
    }
}
