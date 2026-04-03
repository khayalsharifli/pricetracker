package az.khayalsharifli.presentation.ui.feed.screen

import androidx.compose.runtime.Composable
import az.khayalsharifli.presentation.ui.feed.mvi.FeedEffect
import az.khayalsharifli.presentation.ui.feed.mvi.FeedViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun FeedScreen(
    viewModel: FeedViewModel = koinViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState = viewModel.collectAsState().value

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is FeedEffect.NavigateToDetail -> onNavigateToDetail(effect.symbol)
        }
    }

    FeedScreenContent(
        stateReader = { uiState },
        onStockClick = viewModel::onStockClick,
        onToggleFeed = viewModel::onToggleFeed
    )
}