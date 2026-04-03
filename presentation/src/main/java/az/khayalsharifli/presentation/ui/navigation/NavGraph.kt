package az.khayalsharifli.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import az.khayalsharifli.presentation.ui.detail.screen.DetailScreen
import az.khayalsharifli.presentation.ui.feed.screen.FeedScreen

@Composable
fun PriceTrackerNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Feed
    ) {
        composable<Screens.Feed> {
            FeedScreen(
                onNavigateToDetail = { symbol ->
                    navController.navigate(Screens.Detail(symbol = symbol))
                }
            )
        }

        composable<Screens.Detail>(
            deepLinks = listOf(
                navDeepLink<Screens.Detail>(basePath = "stocks://symbol")
            )
        ) {
            DetailScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
