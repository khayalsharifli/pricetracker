package az.khayalsharifli.data.mapper

import az.khayalsharifli.data.websocket.StockDataProvider
import az.khayalsharifli.domain.model.StockPrice
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class StockPriceMessage(
    val symbol: String,
    val price: Double
)

class StockMapper {

    private val json = Json { ignoreUnknownKeys = true }

    private val stockInfoMap = StockDataProvider.stocks.associateBy { it.symbol }

    fun parseMessage(raw: String): StockPriceMessage? {
        return try {
            json.decodeFromString<StockPriceMessage>(raw)
        } catch (e: Exception) {
            null
        }
    }

    fun toStockPrice(
        message: StockPriceMessage,
        previousPrice: Double
    ): StockPrice? {
        val info = stockInfoMap[message.symbol] ?: return null
        return StockPrice(
            symbol = message.symbol,
            name = info.name,
            price = message.price,
            previousPrice = previousPrice,
            description = info.description
        )
    }

    fun createMessage(symbol: String, price: Double): String {
        return json.encodeToString(
            StockPriceMessage.serializer(),
            StockPriceMessage(symbol, price)
        )
    }
}
