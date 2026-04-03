package az.khayalsharifli.presentation.ui.detail.screen

import androidx.compose.runtime.Composable
import az.khayalsharifli.presentation.ui.detail.mvi.DetailEffect
import az.khayalsharifli.presentation.ui.detail.mvi.DetailViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun DetailScreen(
    viewModel: DetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState = viewModel.collectAsState().value

    viewModel.collectSideEffect { effect ->
        when (effect) {
            is DetailEffect.NavigateBack -> onNavigateBack()
        }
    }

    DetailScreenContent(
        stateReader = { uiState },
        onBackClick = viewModel::onBackClick
    )
}