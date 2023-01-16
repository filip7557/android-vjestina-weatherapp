package agency.five.codebase.android.weatherinfoapp.data.di

import agency.five.codebase.android.weatherinfoapp.data.network.WeatherInfoService
import agency.five.codebase.android.weatherinfoapp.data.network.WeatherInfoServiceImpl
import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {

    single<WeatherInfoService> { WeatherInfoServiceImpl(client = get()) }

    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
}
