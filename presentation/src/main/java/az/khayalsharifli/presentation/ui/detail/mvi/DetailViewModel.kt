package az.khayalsharifli.presentation.ui.detail.mvi

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import az.khayalsharifli.domain.usecase.stock.ObserveStocksUseCase
import az.khayalsharifli.presentation.ui.navigation.Screens
import az.khayalsharifli.presentation.ui.util.toFormattedPrice
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@Stable
internal class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val observeStocksUseCase: ObserveStocksUseCase
) : ViewModel(), ContainerHost<DetailScreenState, DetailEffect> {

    private val symbol: String = savedStateHandle.toRoute<Screens.Detail>().symbol

    override val container = container<DetailScreenState, DetailEffect>(DetailScreenState.INITIAL) {
        observeStock()
    }

    private fun observeStock() = intent {
        observeStocksUseCase.execute(Unit).collect { stocks ->
            val stock = stocks.find { it.symbol == symbol } ?: return@collect
            reduce {
                state.copy(
                    symbol = stock.symbol,
                    name = stock.name,
                    formattedPrice = stock.price.toFormattedPrice(),
                    priceChange = stock.priceChange,
                    description = stock.description
                )
            }
        }
    }

    fun onBackClick() = intent {
        postSideEffect(DetailEffect.NavigateBack)
    }

}