package az.khayalsharifli.presentation.ui.detail.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed
import kotlinx.coroutines.delay

@Composable
internal fun PriceCardComposable(
    formattedPrice: String,
    priceChange: PriceChange,
    modifier: Modifier = Modifier
) {
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
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(animatedColor)
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formattedPrice,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(12.dp))
            PriceChangeArrow(priceChange = priceChange)
        }
    }
}

@Composable
private fun PriceChangeArrow(priceChange: PriceChange) {
    val (indicator, color) = when (priceChange) {
        PriceChange.UP -> "\u2191" to PriceGreen
        PriceChange.DOWN -> "\u2193" to PriceRed
        PriceChange.NONE -> "-" to MaterialTheme.colorScheme.onSurfaceVariant
    }
    Text(
        text = indicator,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = color
    )
}
