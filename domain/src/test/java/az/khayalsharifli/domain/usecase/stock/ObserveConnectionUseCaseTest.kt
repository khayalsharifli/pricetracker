package az.khayalsharifli.domain.usecase.stock

import app.cash.turbine.test
import az.khayalsharifli.domain.model.ConnectionState
import az.khayalsharifli.domain.repository.StockRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ObserveConnectionUseCaseTest {

    private lateinit var repository: StockRepository
    private lateinit var useCase: ObserveConnectionUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveConnectionUseCase(repository)
    }

    @Test
    fun `execute delegates to repository observeConnectionState`() = runTest {
        every { repository.observeConnectionState() } returns flowOf(ConnectionState.CONNECTED)

        useCase.execute(Unit).test {
            assertEquals(ConnectionState.CONNECTED, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeConnectionState() }
    }

    @Test
    fun `execute emits DISCONNECTED state`() = runTest {
        every { repository.observeConnectionState() } returns flowOf(ConnectionState.DISCONNECTED)

        useCase.execute(Unit).test {
            assertEquals(ConnectionState.DISCONNECTED, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `execute emits CONNECTING state`() = runTest {
        every { repository.observeConnectionState() } returns flowOf(ConnectionState.CONNECTING)

        useCase.execute(Unit).test {
            assertEquals(ConnectionState.CONNECTING, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `execute emits state transitions`() = runTest {
        every { repository.observeConnectionState() } returns flowOf(
            ConnectionState.DISCONNECTED,
            ConnectionState.CONNECTING,
            ConnectionState.CONNECTED
        )

        useCase.execute(Unit).test {
            assertEquals(ConnectionState.DISCONNECTED, awaitItem())
            assertEquals(ConnectionState.CONNECTING, awaitItem())
            assertEquals(ConnectionState.CONNECTED, awaitItem())
            awaitComplete()
        }
    }
}
