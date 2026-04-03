package az.khayalsharifli.domain.usecase.stock

import az.khayalsharifli.domain.repository.StockRepository

class TogglePriceFeedUseCase(
    private val repository: StockRepository
) {
    fun start() = repository.startPriceFeed()
    fun stop() = repository.stopPriceFeed()
}
