package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.feed.mvi.FeedScreenState
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun StockList(
    stateReader: () -> FeedScreenState,
    onStockClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val stocks = stateReader.read { stocks }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        items(
            items = stocks,
            key = { it.symbol }
        ) { stock ->
            StockRowComposable(
                stateReader = { stock },
                onClick = { onStockClick(stock.symbol) }
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}


@PreviewLightDark
@Composable
private fun StockListPreview() {
    PriceTrackerTheme {
        StockList(
            stateReader = {
                FeedScreenState(
                    stocks = persistentListOf(
                        StockRowState(
                            symbol = "AAPL",
                            name = "Apple Inc.",
                            formattedPrice = "$185.92",
                            priceChange = PriceChange.UP
                        ),
                        StockRowState(
                            symbol = "GOOGL",
                            name = "Alphabet Inc.",
                            formattedPrice = "$141.50",
                            priceChange = PriceChange.DOWN
                        ),
                        StockRowState(
                            symbol = "MSFT",
                            name = "Microsoft Corp.",
                            formattedPrice = "$378.25",
                            priceChange = PriceChange.NONE
                        )
                    )
                )
            },
            onStockClick = {}
        )
    }
}