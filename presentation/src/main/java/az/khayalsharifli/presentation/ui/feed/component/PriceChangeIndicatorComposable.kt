package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed

@Composable
internal fun PriceChangeIndicatorComposable(
    priceChange: PriceChange
) {
    val (text, color) = when (priceChange) {
        PriceChange.UP -> "\u2191" to PriceGreen
        PriceChange.DOWN -> "\u2193" to PriceRed
        PriceChange.NONE -> "-" to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}