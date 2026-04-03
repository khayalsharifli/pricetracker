package az.khayalsharifli.data.websocket

import az.khayalsharifli.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow

interface StockWebSocketService {
    fun observeMessages(): Flow<String>
    fun observeConnectionState(): Flow<ConnectionState>
    fun connect()
    fun sendMessage(message: String)
    fun disconnect()
}
