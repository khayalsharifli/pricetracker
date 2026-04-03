package az.khayalsharifli.presentation.ui.feed.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.base.focusOn
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.feed.component.ConnectionIndicatorComposable
import az.khayalsharifli.presentation.ui.feed.component.FeedToggleButton
import az.khayalsharifli.presentation.ui.feed.component.StockList
import az.khayalsharifli.presentation.ui.feed.mvi.ConnectionIndicatorState
import az.khayalsharifli.presentation.ui.feed.mvi.FeedScreenState
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedScreenContent(
    stateReader: () -> FeedScreenState,
    onStockClick: (String) -> Unit,
    onToggleFeed: () -> Unit
) {
    val isFeedActive = stateReader.read { isFeedActive }
    val connectionStateReader = stateReader.focusOn { connectionState }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Price Tracker") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                navigationIcon = {
                    ConnectionIndicatorComposable(
                        stateReader = connectionStateReader,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                actions = {
                    FeedToggleButton(
                        isFeedActive = isFeedActive,
                        onToggleFeed = onToggleFeed
                    )
                }
            )
        }
    ) { paddingValues ->
        StockList(
            stateReader = stateReader,
            onStockClick = onStockClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        )
    }
}


@PreviewLightDark
@Composable
private fun FeedScreenContentPreview() {
    PriceTrackerTheme {
        val uiState = FeedScreenState(
            isFeedActive = true,
            connectionState = ConnectionIndicatorState(
                isConnected = true,
                label = "Live"
            ),
            stocks = persistentListOf(
                StockRowState(
                    symbol = "NVDA",
                    name = "NVIDIA Corp.",
                    formattedPrice = "$875.40",
                    priceChange = PriceChange.UP
                ),
                StockRowState(
                    symbol = "AAPL",
                    name = "Apple Inc.",
                    formattedPrice = "$178.50",
                    priceChange = PriceChange.DOWN
                ),
                StockRowState(
                    symbol = "GOOG",
                    name = "Alphabet Inc.",
                    formattedPrice = "$141.20",
                    priceChange = PriceChange.NONE
                )
            )
        )
        FeedScreenContent(
            stateReader = { uiState },
            onStockClick = {},
            onToggleFeed = {}
        )
    }
}