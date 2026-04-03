package az.khayalsharifli.pricetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import az.khayalsharifli.presentation.ui.navigation.PriceTrackerNavGraph
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PriceTrackerTheme {
                val navController = rememberNavController()
                PriceTrackerNavGraph(navController = navController)
            }
        }
    }
}
