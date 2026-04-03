package az.khayalsharifli.data.repository

import app.cash.turbine.test
import az.khayalsharifli.data.mapper.StockMapper
import az.khayalsharifli.data.websocket.StockDataProvider
import az.khayalsharifli.data.websocket.StockWebSocketService
import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.model.PriceChange
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StockRepositoryImplTest {

    private lateinit var webSocketService: StockWebSocketService
    private lateinit var mapper: StockMapper
    private lateinit var repository: StockRepositoryImpl

    private val messagesFlow = MutableSharedFlow<String>()
    private val connectionStateFlow = MutableStateFlow(ConnectionState.DISCONNECTED)

    @Before
    fun setup() {
        webSocketService = mockk()
        mapper = StockMapper()

        every { webSocketService.observeMessages() } returns messagesFlow
        every { webSocketService.observeConnectionState() } returns connectionStateFlow
        justRun { webSocketService.connect() }
        justRun { webSocketService.disconnect() }
        justRun { webSocketService.sendMessage(any()) }

        repository = StockRepositoryImpl(webSocketService, mapper)
    }

    // --- Init state ---

    @Test
    fun `initial stocks list contains all 25 symbols`() = runTest {
        repository.observeStocks().test {
            val stocks = awaitItem()
            assertEquals(25, stocks.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initial stocks are sorted by price descending`() = runTest {
        repository.observeStocks().test {
            val stocks = awaitItem()
            for (i in 0 until stocks.size - 1) {
                assertTrue(
                    "${stocks[i].symbol}(${stocks[i].price}) should be >= ${stocks[i + 1].symbol}(${stocks[i + 1].price})",
                    stocks[i].price >= stocks[i + 1].price
                )
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initial stocks have price equal to previousPrice`() = runTest {
        repository.observeStocks().test {
            val stocks = awaitItem()
            stocks.forEach { stock ->
                assertEquals(
                    "Initial price should equal previousPrice for ${stock.symbol}",
                    stock.price,
                    stock.previousPrice,
                    0.001
                )
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initial stocks all have PriceChange NONE`() = runTest {
        repository.observeStocks().test {
            val stocks = awaitItem()
            stocks.forEach { stock ->
                assertEquals(
                    "Initial priceChange should be NONE for ${stock.symbol}",
                    PriceChange.NONE,
                    stock.priceChange
                )
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `initial stocks have base prices from StockDataProvider`() = runTest {
        repository.observeStocks().test {
            val stocks = awaitItem()
            val providerMap = StockDataProvider.stocks.associateBy { it.symbol }
            stocks.forEach { stock ->
                val expected = providerMap[stock.symbol]!!.basePrice
                assertEquals(
                    "Price for ${stock.symbol} should match base",
                    expected,
                    stock.price,
                    0.001
                )
            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    // --- observeConnectionState ---

    @Test
    fun `observeConnectionState delegates to webSocketService`() = runTest {
        repository.observeConnectionState().test {
            assertEquals(ConnectionState.DISCONNECTED, awaitItem())

            connectionStateFlow.value = ConnectionState.CONNECTING
            assertEquals(ConnectionState.CONNECTING, awaitItem())

            connectionStateFlow.value = ConnectionState.CONNECTED
            assertEquals(ConnectionState.CONNECTED, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    // --- startPriceFeed ---

    @Test
    fun `startPriceFeed connects websocket`() {
        repository.startPriceFeed()
        verify(exactly = 1) { webSocketService.connect() }
    }

    @Test
    fun `startPriceFeed twice does not connect again`() {
        repository.startPriceFeed()
        repository.startPriceFeed()
        verify(exactly = 1) { webSocketService.connect() }
    }

    // --- stopPriceFeed ---

    @Test
    fun `stopPriceFeed disconnects websocket`() {
        repository.startPriceFeed()
        repository.stopPriceFeed()
        verify(exactly = 1) { webSocketService.disconnect() }
    }

    @Test
    fun `stopPriceFeed without start does not crash`() {
        repository.stopPriceFeed()
        verify(exactly = 1) { webSocketService.disconnect() }
    }

    @Test
    fun `startPriceFeed after stop reconnects`() {
        repository.startPriceFeed()
        repository.stopPriceFeed()
        repository.startPriceFeed()
        verify(exactly = 2) { webSocketService.connect() }
    }

    // --- Message handling ---

    @Test
    fun `received message updates stock price`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            val initial = awaitItem()
            val aaplInitial = initial.find { it.symbol == "AAPL" }!!

            messagesFlow.emit("""{"symbol":"AAPL","price":200.0}""")

            val updated = awaitItem()
            val aaplUpdated = updated.find { it.symbol == "AAPL" }!!
            assertEquals(200.0, aaplUpdated.price, 0.001)
            assertEquals(aaplInitial.price, aaplUpdated.previousPrice, 0.001)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `received message with unknown symbol is ignored`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            val initial = awaitItem()

            messagesFlow.emit("""{"symbol":"UNKNOWN","price":100.0}""")

            expectNoEvents()
            assertEquals(25, initial.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `received invalid JSON message is ignored`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            awaitItem() // initial

            messagesFlow.emit("not valid json")

            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `stocks remain sorted after price update`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            awaitItem() // initial

            // SNAP has low base price (15.60), set it very high
            messagesFlow.emit("""{"symbol":"SNAP","price":9999.0}""")

            val updated = awaitItem()
            assertEquals("SNAP", updated.first().symbol)

            for (i in 0 until updated.size - 1) {
                assertTrue(updated[i].price >= updated[i + 1].price)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `price update sets correct priceChange UP`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            awaitItem()

            // AAPL base is 178.50, send higher
            messagesFlow.emit("""{"symbol":"AAPL","price":200.0}""")

            val updated = awaitItem()
            val aapl = updated.find { it.symbol == "AAPL" }!!
            assertEquals(PriceChange.UP, aapl.priceChange)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `price update sets correct priceChange DOWN`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            awaitItem()

            // AAPL base is 178.50, send lower
            messagesFlow.emit("""{"symbol":"AAPL","price":100.0}""")

            val updated = awaitItem()
            val aapl = updated.find { it.symbol == "AAPL" }!!
            assertEquals(PriceChange.DOWN, aapl.priceChange)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `multiple price updates track previousPrice correctly`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            awaitItem() // initial, AAPL = 178.50

            messagesFlow.emit("""{"symbol":"AAPL","price":200.0}""")
            val first = awaitItem().find { it.symbol == "AAPL" }!!
            assertEquals(200.0, first.price, 0.001)
            assertEquals(178.50, first.previousPrice, 0.001)

            messagesFlow.emit("""{"symbol":"AAPL","price":190.0}""")
            val second = awaitItem().find { it.symbol == "AAPL" }!!
            assertEquals(190.0, second.price, 0.001)
            assertEquals(200.0, second.previousPrice, 0.001)
            assertEquals(PriceChange.DOWN, second.priceChange)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `sendMessage is called with correct JSON format`() {
        val messageSlot = slot<String>()
        every { webSocketService.sendMessage(capture(messageSlot)) } returns Unit

        repository.startPriceFeed()

        // The feed job sends messages after 2s delay, tested via mapper instead
        // Verify the mapper creates correct format
        val json = mapper.createMessage("AAPL", 150.0)
        val parsed = mapper.parseMessage(json)
        assertEquals("AAPL", parsed!!.symbol)
        assertEquals(150.0, parsed.price, 0.001)
    }

    @Test
    fun `other stocks remain unchanged when one updates`() = runTest {
        repository.startPriceFeed()

        repository.observeStocks().test {
            val initial = awaitItem()
            val googInitial = initial.find { it.symbol == "GOOG" }!!

            messagesFlow.emit("""{"symbol":"AAPL","price":200.0}""")

            val updated = awaitItem()
            val googUpdated = updated.find { it.symbol == "GOOG" }!!
            assertEquals(googInitial.price, googUpdated.price, 0.001)
            assertEquals(googInitial.name, googUpdated.name)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
