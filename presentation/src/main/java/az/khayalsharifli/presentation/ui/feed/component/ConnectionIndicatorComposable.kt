package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.feed.mvi.ConnectionIndicatorState
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed

@Composable
internal fun ConnectionIndicatorComposable(
    stateReader: () -> ConnectionIndicatorState,
    modifier: Modifier = Modifier
) {
    val isConnected = stateReader.read { isConnected }
    val isConnecting = stateReader.read { isConnecting }
    val label = stateReader.read { label }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isConnected -> PriceGreen
                        isConnecting -> Color.Yellow
                        else -> PriceRed
                    }
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}