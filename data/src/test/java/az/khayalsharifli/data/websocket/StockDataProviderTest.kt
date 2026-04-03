package az.khayalsharifli.data.websocket

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StockDataProviderTest {

    @Test
    fun `stocks list contains exactly 25 items`() {
        assertEquals(25, StockDataProvider.stocks.size)
    }

    @Test
    fun `all symbols are unique`() {
        val symbols = StockDataProvider.stocks.map { it.symbol }
        assertEquals(symbols.size, symbols.distinct().size)
    }

    @Test
    fun `all symbols are non-empty`() {
        StockDataProvider.stocks.forEach { stock ->
            assertTrue("Symbol should not be empty", stock.symbol.isNotEmpty())
        }
    }

    @Test
    fun `all names are non-empty`() {
        StockDataProvider.stocks.forEach { stock ->
            assertTrue("Name should not be empty for ${stock.symbol}", stock.name.isNotEmpty())
        }
    }

    @Test
    fun `all descriptions are non-empty`() {
        StockDataProvider.stocks.forEach { stock ->
            assertTrue(
                "Description should not be empty for ${stock.symbol}",
                stock.description.isNotEmpty()
            )
        }
    }

    @Test
    fun `all base prices are positive`() {
        StockDataProvider.stocks.forEach { stock ->
            assertTrue(
                "Base price should be positive for ${stock.symbol}, was ${stock.basePrice}",
                stock.basePrice > 0
            )
        }
    }

    @Test
    fun `contains expected major symbols`() {
        val symbols = StockDataProvider.stocks.map { it.symbol }
        val expectedSymbols = listOf("AAPL", "GOOG", "TSLA", "AMZN", "MSFT", "NVDA", "META")
        expectedSymbols.forEach { expected ->
            assertTrue("Should contain $expected", symbols.contains(expected))
        }
    }

    @Test
    fun `all symbols are uppercase`() {
        StockDataProvider.stocks.forEach { stock ->
            assertEquals(
                "Symbol should be uppercase for ${stock.symbol}",
                stock.symbol.uppercase(),
                stock.symbol
            )
        }
    }

    @Test
    fun `StockInfo data class equality works`() {
        val stock1 = StockInfo("TEST", "Test Corp", "Test desc", 100.0)
        val stock2 = StockInfo("TEST", "Test Corp", "Test desc", 100.0)
        assertEquals(stock1, stock2)
    }

    @Test
    fun `StockInfo data class copy works`() {
        val stock = StockInfo("TEST", "Test Corp", "Test desc", 100.0)
        val copied = stock.copy(basePrice = 200.0)
        assertEquals(200.0, copied.basePrice, 0.001)
        assertEquals("TEST", copied.symbol)
    }
}
