package az.khayalsharifli.domain.usecase.stock

import az.khayalsharifli.domain.base.BaseObserveUseCase
import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow

class ObserveConnectionUseCase(
    private val repository: StockRepository
) : BaseObserveUseCase<Unit, ConnectionState>() {

    override fun execute(params: Unit): Flow<ConnectionState> {
        return repository.observeConnectionState()
    }
}
