package az.khayalsharifli.data.mapper

import az.khayalsharifli.domain.model.PriceChange
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class StockMapperTest {

    private lateinit var mapper: StockMapper

    @Before
    fun setup() {
        mapper = StockMapper()
    }

    // --- parseMessage ---

    @Test
    fun `parseMessage returns StockPriceMessage for valid JSON`() {
        val raw = """{"symbol":"AAPL","price":150.25}"""
        val result = mapper.parseMessage(raw)

        assertNotNull(result)
        assertEquals("AAPL", result!!.symbol)
        assertEquals(150.25, result.price, 0.001)
    }

    @Test
    fun `parseMessage returns null for invalid JSON`() {
        val result = mapper.parseMessage("not a json")
        assertNull(result)
    }

    @Test
    fun `parseMessage returns null for empty string`() {
        val result = mapper.parseMessage("")
        assertNull(result)
    }

    @Test
    fun `parseMessage returns null for partial JSON missing price`() {
        val result = mapper.parseMessage("""{"symbol":"AAPL"}""")
        assertNull(result)
    }

    @Test
    fun `parseMessage returns null for partial JSON missing symbol`() {
        val result = mapper.parseMessage("""{"price":150.0}""")
        assertNull(result)
    }

    @Test
    fun `parseMessage ignores unknown keys`() {
        val raw = """{"symbol":"GOOG","price":141.20,"extra":"ignored"}"""
        val result = mapper.parseMessage(raw)

        assertNotNull(result)
        assertEquals("GOOG", result!!.symbol)
        assertEquals(141.20, result.price, 0.001)
    }

    @Test
    fun `parseMessage handles zero price`() {
        val raw = """{"symbol":"AAPL","price":0.0}"""
        val result = mapper.parseMessage(raw)

        assertNotNull(result)
        assertEquals(0.0, result!!.price, 0.001)
    }

    @Test
    fun `parseMessage handles negative price`() {
        val raw = """{"symbol":"AAPL","price":-5.0}"""
        val result = mapper.parseMessage(raw)

        assertNotNull(result)
        assertEquals(-5.0, result!!.price, 0.001)
    }

    @Test
    fun `parseMessage handles large price value`() {
        val raw = """{"symbol":"AAPL","price":999999.99}"""
        val result = mapper.parseMessage(raw)

        assertNotNull(result)
        assertEquals(999999.99, result!!.price, 0.001)
    }

    // --- toStockPrice ---

    @Test
    fun `toStockPrice returns StockPrice for known symbol`() {
        val message = StockPriceMessage("AAPL", 150.0)
        val result = mapper.toStockPrice(message, 145.0)

        assertNotNull(result)
        assertEquals("AAPL", result!!.symbol)
        assertEquals("Apple Inc.", result.name)
        assertEquals(150.0, result.price, 0.001)
        assertEquals(145.0, result.previousPrice, 0.001)
        assertEquals(PriceChange.UP, result.priceChange)
    }

    @Test
    fun `toStockPrice returns null for unknown symbol`() {
        val message = StockPriceMessage("UNKNOWN", 100.0)
        val result = mapper.toStockPrice(message, 90.0)
        assertNull(result)
    }

    @Test
    fun `toStockPrice maps description from StockDataProvider`() {
        val message = StockPriceMessage("NVDA", 900.0)
        val result = mapper.toStockPrice(message, 875.0)

        assertNotNull(result)
        assertEquals("NVIDIA Corp.", result!!.name)
        assert(result.description.contains("GPU"))
    }

    @Test
    fun `toStockPrice sets correct priceChange DOWN`() {
        val message = StockPriceMessage("AAPL", 140.0)
        val result = mapper.toStockPrice(message, 150.0)

        assertNotNull(result)
        assertEquals(PriceChange.DOWN, result!!.priceChange)
    }

    @Test
    fun `toStockPrice sets correct priceChange NONE when prices equal`() {
        val message = StockPriceMessage("AAPL", 150.0)
        val result = mapper.toStockPrice(message, 150.0)

        assertNotNull(result)
        assertEquals(PriceChange.NONE, result!!.priceChange)
    }

    @Test
    fun `toStockPrice works for all 25 known symbols`() {
        val knownSymbols = listOf(
            "AAPL", "GOOG", "TSLA", "AMZN", "MSFT", "NVDA", "META", "NFLX",
            "AMD", "INTC", "CRM", "ORCL", "PYPL", "UBER", "SHOP", "SQ",
            "SNAP", "SPOT", "ZM", "COIN", "ROKU", "PINS", "TWLO", "DDOG", "NET"
        )

        knownSymbols.forEach { symbol ->
            val message = StockPriceMessage(symbol, 100.0)
            val result = mapper.toStockPrice(message, 95.0)
            assertNotNull("toStockPrice should not return null for $symbol", result)
            assertEquals(symbol, result!!.symbol)
        }
    }

    // --- createMessage ---

    @Test
    fun `createMessage returns valid JSON`() {
        val result = mapper.createMessage("AAPL", 150.25)
        val parsed = mapper.parseMessage(result)

        assertNotNull(parsed)
        assertEquals("AAPL", parsed!!.symbol)
        assertEquals(150.25, parsed.price, 0.001)
    }

    @Test
    fun `createMessage roundtrip preserves data`() {
        val symbol = "TSLA"
        val price = 245.80

        val json = mapper.createMessage(symbol, price)
        val parsed = mapper.parseMessage(json)

        assertNotNull(parsed)
        assertEquals(symbol, parsed!!.symbol)
        assertEquals(price, parsed.price, 0.001)
    }

    @Test
    fun `createMessage handles zero price`() {
        val json = mapper.createMessage("AAPL", 0.0)
        val parsed = mapper.parseMessage(json)

        assertNotNull(parsed)
        assertEquals(0.0, parsed!!.price, 0.001)
    }

    @Test
    fun `createMessage handles very small price`() {
        val json = mapper.createMessage("AAPL", 0.01)
        val parsed = mapper.parseMessage(json)

        assertNotNull(parsed)
        assertEquals(0.01, parsed!!.price, 0.001)
    }
}
