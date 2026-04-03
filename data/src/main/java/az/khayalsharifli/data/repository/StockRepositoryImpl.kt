package az.khayalsharifli.data.repository

import az.khayalsharifli.data.mapper.StockMapper
import az.khayalsharifli.data.websocket.StockDataProvider
import az.khayalsharifli.data.websocket.StockWebSocketService
import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.model.StockPrice
import az.khayalsharifli.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class StockRepositoryImpl(
    private val webSocketService: StockWebSocketService,
    private val mapper: StockMapper
) : StockRepository {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var feedJob: Job? = null
    private var messageListenerJob: Job? = null

    private val _stocks = MutableStateFlow<List<StockPrice>>(emptyList())
    private val currentPrices = mutableMapOf<String, Double>()

    init {
        StockDataProvider.stocks.forEach { stock ->
            currentPrices[stock.symbol] = stock.basePrice
        }
        _stocks.value = StockDataProvider.stocks.map { stock ->
            StockPrice(
                symbol = stock.symbol,
                name = stock.name,
                price = stock.basePrice,
                previousPrice = stock.basePrice,
                description = stock.description
            )
        }.sortedByDescending { it.price }
    }

    override fun observeStocks(): Flow<List<StockPrice>> = _stocks.asStateFlow()

    override fun observeConnectionState(): Flow<ConnectionState> =
        webSocketService.observeConnectionState()

    override fun startPriceFeed() {
        if (feedJob?.isActive == true) return

        webSocketService.connect()
        startListeningMessages()
        startSendingPrices()
    }

    override fun stopPriceFeed() {
        feedJob?.cancel()
        feedJob = null
        messageListenerJob?.cancel()
        messageListenerJob = null
        webSocketService.disconnect()
    }

    private fun startListeningMessages() {
        messageListenerJob?.cancel()
        messageListenerJob = webSocketService.observeMessages()
            .onEach { raw ->
                val message = mapper.parseMessage(raw) ?: return@onEach
                val previousPrice = currentPrices[message.symbol] ?: return@onEach
                val stockPrice = mapper.toStockPrice(message, previousPrice) ?: return@onEach

                currentPrices[message.symbol] = message.price

                _stocks.update { currentList ->
                    currentList.map { existing ->
                        if (existing.symbol == stockPrice.symbol) stockPrice else existing
                    }.sortedByDescending { it.price }
                }
            }
            .launchIn(scope)
    }

    private fun startSendingPrices() {
        feedJob = scope.launch {
            while (true) {
                delay(2000L)
                StockDataProvider.stocks.forEach { stock ->
                    val currentPrice = currentPrices[stock.symbol] ?: stock.basePrice
                    val changePercent = Random.nextDouble(-0.03, 0.03)
                    val newPrice = (currentPrice * (1 + changePercent))
                        .coerceAtLeast(0.01)
                        .let { Math.round(it * 100.0) / 100.0 }

                    val message = mapper.createMessage(stock.symbol, newPrice)
                    webSocketService.sendMessage(message)
                }
            }
        }
    }
}
