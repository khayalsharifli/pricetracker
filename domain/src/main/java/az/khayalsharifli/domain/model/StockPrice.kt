package az.khayalsharifli.domain.model

data class StockPrice(
    val symbol: String,
    val name: String,
    val price: Double,
    val previousPrice: Double,
    val description: String
) {
    val priceChange: PriceChange
        get() = when {
            price > previousPrice -> PriceChange.UP
            price < previousPrice -> PriceChange.DOWN
            else -> PriceChange.NONE
        }
}

enum class PriceChange {
    UP, DOWN, NONE
}
