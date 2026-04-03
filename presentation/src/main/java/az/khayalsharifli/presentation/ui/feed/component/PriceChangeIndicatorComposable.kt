package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import androidx.compose.ui.tooling.preview.PreviewLightDark

@PreviewLightDark
@Composable
private fun PriceChangeIndicatorUpPreview() {
    PriceTrackerTheme {
        PriceChangeIndicatorComposable(priceChange = PriceChange.UP)
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeIndicatorDownPreview() {
    PriceTrackerTheme {
        PriceChangeIndicatorComposable(priceChange = PriceChange.DOWN)
    }
}

@PreviewLightDark
@Composable
private fun PriceChangeIndicatorNonePreview() {
    PriceTrackerTheme {
        PriceChangeIndicatorComposable(priceChange = PriceChange.NONE)
    }
}

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