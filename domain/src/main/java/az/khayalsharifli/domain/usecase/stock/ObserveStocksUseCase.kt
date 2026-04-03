package az.khayalsharifli.domain.usecase.stock

import az.khayalsharifli.domain.base.BaseObserveUseCase
import az.khayalsharifli.domain.model.StockPrice
import az.khayalsharifli.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow

class ObserveStocksUseCase(
    private val repository: StockRepository
) : BaseObserveUseCase<Unit, List<StockPrice>>() {

    override fun execute(params: Unit): Flow<List<StockPrice>> {
        return repository.observeStocks()
    }
}
