package az.khayalsharifli.domain.usecase.stock

import az.khayalsharifli.domain.repository.StockRepository
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test

class TogglePriceFeedUseCaseTest {

    private lateinit var repository: StockRepository
    private lateinit var useCase: TogglePriceFeedUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = TogglePriceFeedUseCase(repository)
    }

    @Test
    fun `start delegates to repository startPriceFeed`() {
        justRun { repository.startPriceFeed() }

        useCase.start()

        verify(exactly = 1) { repository.startPriceFeed() }
    }

    @Test
    fun `stop delegates to repository stopPriceFeed`() {
        justRun { repository.stopPriceFeed() }

        useCase.stop()

        verify(exactly = 1) { repository.stopPriceFeed() }
    }

    @Test
    fun `start then stop calls repository in correct order`() {
        justRun { repository.startPriceFeed() }
        justRun { repository.stopPriceFeed() }

        useCase.start()
        useCase.stop()

        verifyOrder {
            repository.startPriceFeed()
            repository.stopPriceFeed()
        }
    }

    @Test
    fun `multiple start calls delegate each time`() {
        justRun { repository.startPriceFeed() }

        useCase.start()
        useCase.start()
        useCase.start()

        verify(exactly = 3) { repository.startPriceFeed() }
    }

    @Test
    fun `multiple stop calls delegate each time`() {
        justRun { repository.stopPriceFeed() }

        useCase.stop()
        useCase.stop()

        verify(exactly = 2) { repository.stopPriceFeed() }
    }
}
