package agency.five.codebase.android.weatherinfoapp

import agency.five.codebase.android.weatherinfoapp.data.di.dataModule
import agency.five.codebase.android.weatherinfoapp.data.di.databaseModule
import agency.five.codebase.android.weatherinfoapp.data.di.networkModule
import agency.five.codebase.android.weatherinfoapp.ui.favorites.di.favoritesModule
import agency.five.codebase.android.weatherinfoapp.ui.search.di.searchModule
import agency.five.codebase.android.weatherinfoapp.ui.weatherInfo.di.weatherInfoModule
import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

lateinit var fusedLocationClient: FusedLocationProviderClient

class WeatherInfoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherInfoApp)
            modules(
                dataModule,
                networkModule,
                databaseModule,
                weatherInfoModule,
                favoritesModule,
                searchModule
            )
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
}
