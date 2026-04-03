package az.khayalsharifli.domain.exceptions

sealed class HandledException(message: String) : Exception(message) {
    class NetworkError(message: String = "Network error") : HandledException(message)
    class WebSocketError(message: String = "WebSocket error") : HandledException(message)
    class UnknownError(message: String = "Unknown error") : HandledException(message)
}
