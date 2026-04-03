package az.khayalsharifli.presentation.ui.detail.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.base.read
import az.khayalsharifli.presentation.ui.detail.component.DetailBody
import az.khayalsharifli.presentation.ui.detail.mvi.DetailScreenState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailScreenContent(
    stateReader: () -> DetailScreenState, onBackClick: () -> Unit
) {
    val symbol = stateReader.read { symbol }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(symbol) }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ), navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        }) { paddingValues ->
        DetailBody(
            stateReader = stateReader,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        )
    }
}


@PreviewLightDark
@Composable
private fun DetailScreenContentPreview() {
    PriceTrackerTheme {
        val uiState = DetailScreenState(
            symbol = "NVDA",
            name = "NVIDIA Corp.",
            formattedPrice = "$875.40",
            priceChange = PriceChange.UP,
            description = "NVIDIA designs GPU-accelerated computing platforms for gaming, professional visualization, data centers, and automotive."
        )
        DetailScreenContent(stateReader = { uiState }, onBackClick = {})
    }
}