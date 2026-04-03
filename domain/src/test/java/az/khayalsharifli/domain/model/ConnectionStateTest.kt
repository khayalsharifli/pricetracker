package az.khayalsharifli.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConnectionStateTest {

    @Test
    fun `enum contains all expected values`() {
        val values = ConnectionState.entries
        assertEquals(3, values.size)
        assertTrue(values.contains(ConnectionState.CONNECTED))
        assertTrue(values.contains(ConnectionState.DISCONNECTED))
        assertTrue(values.contains(ConnectionState.CONNECTING))
    }

    @Test
    fun `valueOf returns correct enum for CONNECTED`() {
        assertEquals(ConnectionState.CONNECTED, ConnectionState.valueOf("CONNECTED"))
    }

    @Test
    fun `valueOf returns correct enum for DISCONNECTED`() {
        assertEquals(ConnectionState.DISCONNECTED, ConnectionState.valueOf("DISCONNECTED"))
    }

    @Test
    fun `valueOf returns correct enum for CONNECTING`() {
        assertEquals(ConnectionState.CONNECTING, ConnectionState.valueOf("CONNECTING"))
    }
}
