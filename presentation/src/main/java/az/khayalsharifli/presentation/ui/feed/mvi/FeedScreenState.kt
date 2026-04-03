package az.khayalsharifli.presentation.ui.feed.mvi

import az.khayalsharifli.domain.model.PriceChange
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class FeedScreenState(
    val isFeedActive: Boolean = false,
    val stocks: ImmutableList<StockRowState> = persistentListOf(),
    val connectionState: ConnectionIndicatorState = ConnectionIndicatorState()
) {
    companion object {
        val INITIAL = FeedScreenState()
    }
}

internal data class ConnectionIndicatorState(
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val label: String = "Offline"
)

internal data class StockRowState(
    val symbol: String = "",
    val name: String = "",
    val formattedPrice: String = "$0.00",
    val priceChange: PriceChange = PriceChange.NONE
)