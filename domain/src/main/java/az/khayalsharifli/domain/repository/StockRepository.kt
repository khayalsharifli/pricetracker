package az.khayalsharifli.domain.repository

import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.model.StockPrice
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun observeStocks(): Flow<List<StockPrice>>
    fun observeConnectionState(): Flow<ConnectionState>
    fun startPriceFeed()
    fun stopPriceFeed()
}
