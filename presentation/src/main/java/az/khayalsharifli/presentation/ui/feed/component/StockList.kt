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