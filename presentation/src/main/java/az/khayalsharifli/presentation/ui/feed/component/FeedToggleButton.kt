package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import az.khayalsharifli.presentation.ui.theme.PriceGreen
import az.khayalsharifli.presentation.ui.theme.PriceRed
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import androidx.compose.ui.tooling.preview.PreviewLightDark


@Composable
internal fun FeedToggleButton(
    isFeedActive: Boolean,
    onToggleFeed: () -> Unit
) {
    Button(
        onClick = onToggleFeed,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFeedActive) PriceRed else PriceGreen
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(
            text = if (isFeedActive) "Stop" else "Start",
            color = Color.White
        )
    }
}

@PreviewLightDark
@Composable
private fun FeedToggleButtonActivePreview() {
    PriceTrackerTheme {
        FeedToggleButton(isFeedActive = true, onToggleFeed = {})
    }
}

@PreviewLightDark
@Composable
private fun FeedToggleButtonInactivePreview() {
    PriceTrackerTheme {
        FeedToggleButton(isFeedActive = false, onToggleFeed = {})
    }
}
