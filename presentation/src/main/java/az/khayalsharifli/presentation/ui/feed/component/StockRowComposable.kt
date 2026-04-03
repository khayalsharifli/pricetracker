package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed
import kotlinx.coroutines.delay
import androidx.compose.ui.tooling.preview.PreviewLightDark
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme

@Composable
internal fun StockRowComposable(
    stateReader: () -> StockRowState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val symbol = stateReader.read { symbol }
    val name = stateReader.read { name }
    val formattedPrice = stateReader.read { formattedPrice }
    val priceChange = stateReader.read { priceChange }

    var flashColor by remember { mutableStateOf(Color.Transparent) }

    LaunchedEffect(formattedPrice) {
        flashColor = when (priceChange) {
            PriceChange.UP -> PriceGreen.copy(alpha = 0.15f)
            PriceChange.DOWN -> PriceRed.copy(alpha = 0.15f)
            PriceChange.NONE -> Color.Transparent
        }
        delay(1000L)
        flashColor = Color.Transparent
    }

    val animatedColor by animateColorAsState(
        targetValue = flashColor,
        animationSpec = tween(durationMillis = 300),
        label = "flash"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(animatedColor)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formattedPrice,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                PriceChangeIndicatorComposable(priceChange = priceChange)
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun StockRowUpPreview() {
    PriceTrackerTheme {
        StockRowComposable(
            stateReader = {
                StockRowState(
                    symbol = "AAPL",
                    name = "Apple Inc.",
                    formattedPrice = "$185.92",
                    priceChange = PriceChange.UP
                )
            },
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun StockRowDownPreview() {
    PriceTrackerTheme {
        StockRowComposable(
            stateReader = {
                StockRowState(
                    symbol = "GOOGL",
                    name = "Alphabet Inc.",
                    formattedPrice = "$141.50",
                    priceChange = PriceChange.DOWN
                )
            },
            onClick = {}
        )
    }
}
