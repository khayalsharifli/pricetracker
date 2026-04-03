package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.feed.mvi.FeedScreenState
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class StockListTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysAllStockRows() {
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("AAPL", "Apple Inc.", "$178.50", PriceChange.UP),
                StockRowState("GOOG", "Alphabet Inc.", "$141.20", PriceChange.DOWN),
                StockRowState("TSLA", "Tesla Inc.", "$245.80", PriceChange.NONE)
            )
        )

        composeTestRule.setContent {
            PriceTrackerTheme {
                StockList(
                    stateReader = { state },
                    onStockClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").assertIsDisplayed()
        composeTestRule.onNodeWithText("GOOG").assertIsDisplayed()
        composeTestRule.onNodeWithText("TSLA").assertIsDisplayed()
    }

    @Test
    fun emptyListShowsNoStocks() {
        val state = FeedScreenState(stocks = persistentListOf())

        composeTestRule.setContent {
            PriceTrackerTheme {
                StockList(
                    stateReader = { state },
                    onStockClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").assertDoesNotExist()
    }

    @Test
    fun clickOnStockRowTriggersCallback() {
        var clickedSymbol = ""
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("NVDA", "NVIDIA Corp.", "$875.40", PriceChange.UP)
            )
        )

        composeTestRule.setContent {
            PriceTrackerTheme {
                StockList(
                    stateReader = { state },
                    onStockClick = { clickedSymbol = it }
                )
            }
        }

        composeTestRule.onNodeWithText("NVDA").performClick()
        assertEquals("NVDA", clickedSymbol)
    }

    @Test
    fun displaysCorrectPricesForEachRow() {
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("AAPL", "Apple Inc.", "$178.50", PriceChange.UP),
                StockRowState("GOOG", "Alphabet Inc.", "$141.20", PriceChange.DOWN)
            )
        )

        composeTestRule.setContent {
            PriceTrackerTheme {
                StockList(
                    stateReader = { state },
                    onStockClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("$178.50").assertIsDisplayed()
        composeTestRule.onNodeWithText("$141.20").assertIsDisplayed()
    }
}
