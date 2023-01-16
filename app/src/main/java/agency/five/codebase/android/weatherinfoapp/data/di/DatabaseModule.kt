package agency.five.codebase.android.weatherinfoapp.data.di

import agency.five.codebase.android.weatherinfoapp.data.database.WeatherInfoAppDatabase
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private const val APP_DATABASE_NAME = "weatherinfoapp_database.db"

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            WeatherInfoAppDatabase::class.java,
            APP_DATABASE_NAME,
        ).build()
    }
    fun provideFavoriteLocationDao(database: WeatherInfoAppDatabase) = database.favoriteLocationDao()
    single { provideFavoriteLocationDao(get()) }
}
