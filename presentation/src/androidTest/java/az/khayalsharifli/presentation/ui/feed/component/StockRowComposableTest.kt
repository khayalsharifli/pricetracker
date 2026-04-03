package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class StockRowComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createState(
        symbol: String = "AAPL",
        name: String = "Apple Inc.",
        formattedPrice: String = "$178.50",
        priceChange: PriceChange = PriceChange.NONE
    ) = StockRowState(
        symbol = symbol,
        name = name,
        formattedPrice = formattedPrice,
        priceChange = priceChange
    )

    @Test
    fun displaysSymbol() {
        val state = createState(symbol = "NVDA")
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("NVDA").assertIsDisplayed()
    }

    @Test
    fun displaysName() {
        val state = createState(name = "NVIDIA Corp.")
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("NVIDIA Corp.").assertIsDisplayed()
    }

    @Test
    fun displaysFormattedPrice() {
        val state = createState(formattedPrice = "$875.40")
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("$875.40").assertIsDisplayed()
    }

    @Test
    fun displaysUpArrowWhenPriceUp() {
        val state = createState(priceChange = PriceChange.UP)
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }

    @Test
    fun displaysDownArrowWhenPriceDown() {
        val state = createState(priceChange = PriceChange.DOWN)
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }

    @Test
    fun displaysDashWhenPriceNone() {
        val state = createState(priceChange = PriceChange.NONE)
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun clickTriggersOnClick() {
        var clicked = false
        val state = createState()
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(
                    stateReader = { state },
                    onClick = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").performClick()
        assertTrue(clicked)
    }

    @Test
    fun displaysAllFieldsTogether() {
        val state = createState(
            symbol = "TSLA",
            name = "Tesla Inc.",
            formattedPrice = "$245.80",
            priceChange = PriceChange.UP
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                StockRowComposable(stateReader = { state }, onClick = {})
            }
        }

        composeTestRule.onNodeWithText("TSLA").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tesla Inc.").assertIsDisplayed()
        composeTestRule.onNodeWithText("$245.80").assertIsDisplayed()
        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }
}
