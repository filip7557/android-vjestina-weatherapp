package agency.five.codebase.android.weatherinfoapp

import agency.five.codebase.android.weatherinfoapp.ui.main.MainScreen
import agency.five.codebase.android.weatherinfoapp.ui.theme.WeatherInfoTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherInfoTheme {
               MainScreen()
            }
        }
    }
}
