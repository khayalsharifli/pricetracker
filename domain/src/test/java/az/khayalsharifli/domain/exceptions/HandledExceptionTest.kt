package az.khayalsharifli.domain.exceptions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HandledExceptionTest {

    @Test
    fun `NetworkError has default message`() {
        val error = HandledException.NetworkError()
        assertEquals("Network error", error.message)
    }

    @Test
    fun `NetworkError accepts custom message`() {
        val error = HandledException.NetworkError("No internet")
        assertEquals("No internet", error.message)
    }

    @Test
    fun `WebSocketError has default message`() {
        val error = HandledException.WebSocketError()
        assertEquals("WebSocket error", error.message)
    }

    @Test
    fun `WebSocketError accepts custom message`() {
        val error = HandledException.WebSocketError("Connection lost")
        assertEquals("Connection lost", error.message)
    }

    @Test
    fun `UnknownError has default message`() {
        val error = HandledException.UnknownError()
        assertEquals("Unknown error", error.message)
    }

    @Test
    fun `UnknownError accepts custom message`() {
        val error = HandledException.UnknownError("Something went wrong")
        assertEquals("Something went wrong", error.message)
    }

    @Test
    fun `NetworkError is instance of HandledException`() {
        val error = HandledException.NetworkError()
        assertTrue(error is HandledException)
    }

    @Test
    fun `WebSocketError is instance of HandledException`() {
        val error = HandledException.WebSocketError()
        assertTrue(error is HandledException)
    }

    @Test
    fun `UnknownError is instance of HandledException`() {
        val error = HandledException.UnknownError()
        assertTrue(error is HandledException)
    }

    @Test
    fun `all exceptions are instances of Exception`() {
        assertTrue(HandledException.NetworkError() is Exception)
        assertTrue(HandledException.WebSocketError() is Exception)
        assertTrue(HandledException.UnknownError() is Exception)
    }
}
