package az.khayalsharifli.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class StockPriceTest {

    @Test
    fun `priceChange returns UP when price is greater than previousPrice`() {
        val stock = createStockPrice(price = 150.0, previousPrice = 100.0)
        assertEquals(PriceChange.UP, stock.priceChange)
    }

    @Test
    fun `priceChange returns DOWN when price is less than previousPrice`() {
        val stock = createStockPrice(price = 80.0, previousPrice = 100.0)
        assertEquals(PriceChange.DOWN, stock.priceChange)
    }

    @Test
    fun `priceChange returns NONE when price equals previousPrice`() {
        val stock = createStockPrice(price = 100.0, previousPrice = 100.0)
        assertEquals(PriceChange.NONE, stock.priceChange)
    }

    @Test
    fun `priceChange returns UP for minimal increase`() {
        val stock = createStockPrice(price = 100.01, previousPrice = 100.0)
        assertEquals(PriceChange.UP, stock.priceChange)
    }

    @Test
    fun `priceChange returns DOWN for minimal decrease`() {
        val stock = createStockPrice(price = 99.99, previousPrice = 100.0)
        assertEquals(PriceChange.DOWN, stock.priceChange)
    }

    @Test
    fun `priceChange returns NONE when both prices are zero`() {
        val stock = createStockPrice(price = 0.0, previousPrice = 0.0)
        assertEquals(PriceChange.NONE, stock.priceChange)
    }

    @Test
    fun `priceChange returns UP when previousPrice is zero and price is positive`() {
        val stock = createStockPrice(price = 50.0, previousPrice = 0.0)
        assertEquals(PriceChange.UP, stock.priceChange)
    }

    @Test
    fun `data class equality works correctly`() {
        val stock1 = createStockPrice(price = 100.0, previousPrice = 90.0)
        val stock2 = createStockPrice(price = 100.0, previousPrice = 90.0)
        assertEquals(stock1, stock2)
    }

    @Test
    fun `data class copy updates price correctly`() {
        val original = createStockPrice(price = 100.0, previousPrice = 90.0)
        val updated = original.copy(price = 120.0, previousPrice = 100.0)
        assertEquals(120.0, updated.price, 0.001)
        assertEquals(100.0, updated.previousPrice, 0.001)
        assertEquals(PriceChange.UP, updated.priceChange)
    }

    private fun createStockPrice(
        symbol: String = "AAPL",
        name: String = "Apple Inc.",
        price: Double = 100.0,
        previousPrice: Double = 100.0,
        description: String = "Test description"
    ) = StockPrice(
        symbol = symbol,
        name = name,
        price = price,
        previousPrice = previousPrice,
        description = description
    )
}
