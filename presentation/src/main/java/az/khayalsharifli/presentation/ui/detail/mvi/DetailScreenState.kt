package az.khayalsharifli.presentation.ui.detail.mvi

import az.khayalsharifli.domain.model.PriceChange

internal data class DetailScreenState(
    val symbol: String = "",
    val name: String = "",
    val formattedPrice: String = "$0.00",
    val priceChange: PriceChange = PriceChange.NONE,
    val description: String = ""
) {
    companion object {
        val INITIAL = DetailScreenState()
    }
}