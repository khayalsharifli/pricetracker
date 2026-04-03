package az.khayalsharifli.data.websocket

import az.khayalsharifli.domain.model.ConnectionState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class StockWebSocketServiceImpl(
    private val client: OkHttpClient
) : StockWebSocketService {

    private var webSocket: WebSocket? = null

    private val _messages = MutableSharedFlow<String>(
        extraBufferCapacity = 100,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)

    override fun observeMessages(): Flow<String> = _messages.asSharedFlow()

    override fun observeConnectionState(): Flow<ConnectionState> = _connectionState.asStateFlow()

    override fun connect() {
        if (webSocket != null) return

        _connectionState.value = ConnectionState.CONNECTING

        val request = Request.Builder()
            .url(WS_URL)
            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _connectionState.value = ConnectionState.CONNECTED
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                _messages.tryEmit(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                _connectionState.value = ConnectionState.DISCONNECTED
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _connectionState.value = ConnectionState.DISCONNECTED
                this@StockWebSocketServiceImpl.webSocket = null
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _connectionState.value = ConnectionState.DISCONNECTED
                this@StockWebSocketServiceImpl.webSocket = null
            }
        })
    }

    override fun sendMessage(message: String) {
        webSocket?.send(message)
    }

    override fun disconnect() {
        webSocket?.close(NORMAL_CLOSURE_STATUS, "User stopped feed")
        webSocket = null
        _connectionState.value = ConnectionState.DISCONNECTED
    }

    companion object {
        private const val WS_URL = "wss://ws.postman-echo.com/raw"
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}
