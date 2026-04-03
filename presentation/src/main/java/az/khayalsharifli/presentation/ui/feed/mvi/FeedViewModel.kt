package az.khayalsharifli.presentation.ui.feed.mvi

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.usecase.stock.ObserveConnectionUseCase
import az.khayalsharifli.domain.usecase.stock.ObserveStocksUseCase
import az.khayalsharifli.domain.usecase.stock.TogglePriceFeedUseCase
import az.khayalsharifli.presentation.ui.util.toFormattedPrice
import kotlinx.collections.immutable.toPersistentList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@Stable
internal class FeedViewModel(
    private val observeStocksUseCase: ObserveStocksUseCase,
    private val observeConnectionUseCase: ObserveConnectionUseCase,
    private val togglePriceFeedUseCase: TogglePriceFeedUseCase
) : ViewModel(), ContainerHost<FeedScreenState, FeedEffect> {

    override val container = container<FeedScreenState, FeedEffect>(FeedScreenState.INITIAL) {
        observeStocks()
        observeConnection()
    }

    private fun observeStocks() = intent {
        observeStocksUseCase.execute(Unit).collect { stocks ->
            reduce {
                state.copy(
                    stocks = stocks.map { stock ->
                        StockRowState(
                            symbol = stock.symbol,
                            name = stock.name,
                            formattedPrice = stock.price.toFormattedPrice(),
                            priceChange = stock.priceChange
                        )
                    }.toPersistentList()
                )
            }
        }
    }

    private fun observeConnection() = intent {
        observeConnectionUseCase.execute(Unit).collect { connectionState ->
            reduce {
                state.copy(
                    connectionState = ConnectionIndicatorState(
                        isConnected = connectionState == ConnectionState.CONNECTED,
                        isConnecting = connectionState == ConnectionState.CONNECTING,
                        label = when (connectionState) {
                            ConnectionState.CONNECTED -> "Live"
                            ConnectionState.CONNECTING -> "Connecting..."
                            ConnectionState.DISCONNECTED -> "Offline"
                        }
                    )
                )
            }
        }
    }

    fun onToggleFeed() = intent {
        val isActive = state.isFeedActive
        if (isActive) {
            togglePriceFeedUseCase.stop()
        } else {
            togglePriceFeedUseCase.start()
        }
        reduce { state.copy(isFeedActive = !isActive) }
    }

    fun onStockClick(symbol: String) = intent {
        postSideEffect(FeedEffect.NavigateToDetail(symbol))
    }

    override fun onCleared() {
        super.onCleared()
        togglePriceFeedUseCase.stop()
    }
}
