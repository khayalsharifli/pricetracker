package az.khayalsharifli.presentation.ui.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.detail.mvi.DetailScreenState
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import androidx.compose.ui.tooling.preview.PreviewLightDark

@Composable
internal fun DetailBody(
    stateReader: () -> DetailScreenState,
    modifier: Modifier = Modifier
) {
    val name = stateReader.read { name }
    val formattedPrice = stateReader.read { formattedPrice }
    val priceChange = stateReader.read { priceChange }
    val description = stateReader.read { description }

    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        PriceCardComposable(
            formattedPrice = formattedPrice,
            priceChange = priceChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "About",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@PreviewLightDark
@Composable
private fun DetailBodyPreview() {
    PriceTrackerTheme {
        DetailBody(
            stateReader = {
                DetailScreenState(
                    symbol = "AAPL",
                    name = "Apple Inc.",
                    formattedPrice = "$185.92",
                    priceChange = PriceChange.UP,
                    description = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."
                )
            }
        )
    }
}