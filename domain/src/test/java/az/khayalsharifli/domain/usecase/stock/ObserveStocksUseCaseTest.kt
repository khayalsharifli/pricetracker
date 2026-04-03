package az.khayalsharifli.domain.usecase.stock

import app.cash.turbine.test
import az.khayalsharifli.domain.model.StockPrice
import az.khayalsharifli.domain.repository.StockRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ObserveStocksUseCaseTest {

    private lateinit var repository: StockRepository
    private lateinit var useCase: ObserveStocksUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveStocksUseCase(repository)
    }

    @Test
    fun `execute delegates to repository observeStocks`() = runTest {
        val stocks = listOf(
            StockPrice("AAPL", "Apple", 150.0, 145.0, "desc")
        )
        every { repository.observeStocks() } returns flowOf(stocks)

        useCase.execute(Unit).test {
            assertEquals(stocks, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeStocks() }
    }

    @Test
    fun `execute emits empty list when repository returns empty`() = runTest {
        every { repository.observeStocks() } returns flowOf(emptyList())

        useCase.execute(Unit).test {
            assertTrue(awaitItem().isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `execute emits multiple updates from repository`() = runTest {
        val first = listOf(StockPrice("AAPL", "Apple", 150.0, 145.0, "desc"))
        val second = listOf(
            StockPrice("AAPL", "Apple", 155.0, 150.0, "desc"),
            StockPrice("GOOG", "Alphabet", 140.0, 138.0, "desc")
        )
        every { repository.observeStocks() } returns flowOf(first, second)

        useCase.execute(Unit).test {
            assertEquals(1, awaitItem().size)
            assertEquals(2, awaitItem().size)
            awaitComplete()
        }
    }
}
