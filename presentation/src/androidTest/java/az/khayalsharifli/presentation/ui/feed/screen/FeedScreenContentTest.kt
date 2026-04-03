package az.khayalsharifli.presentation.ui.feed.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.feed.mvi.ConnectionIndicatorState
import az.khayalsharifli.presentation.ui.feed.mvi.FeedScreenState
import az.khayalsharifli.presentation.ui.feed.mvi.StockRowState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FeedScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysTopBarTitle() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { FeedScreenState.INITIAL },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Price Tracker").assertIsDisplayed()
    }

    @Test
    fun displaysStartButtonWhenFeedInactive() {
        val state = FeedScreenState(isFeedActive = false)
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Start").assertIsDisplayed()
    }

    @Test
    fun displaysStopButtonWhenFeedActive() {
        val state = FeedScreenState(isFeedActive = true)
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Stop").assertIsDisplayed()
    }

    @Test
    fun displaysConnectionStatusOffline() {
        val state = FeedScreenState(
            connectionState = ConnectionIndicatorState(
                isConnected = false,
                label = "Offline"
            )
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Offline").assertIsDisplayed()
    }

    @Test
    fun displaysConnectionStatusLive() {
        val state = FeedScreenState(
            connectionState = ConnectionIndicatorState(
                isConnected = true,
                label = "Live"
            )
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Live").assertIsDisplayed()
    }

    @Test
    fun displaysStockItems() {
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("AAPL", "Apple Inc.", "$178.50", PriceChange.UP),
                StockRowState("NVDA", "NVIDIA Corp.", "$875.40", PriceChange.DOWN)
            )
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").assertIsDisplayed()
        composeTestRule.onNodeWithText("Apple Inc.").assertIsDisplayed()
        composeTestRule.onNodeWithText("$178.50").assertIsDisplayed()
        composeTestRule.onNodeWithText("NVDA").assertIsDisplayed()
        composeTestRule.onNodeWithText("NVIDIA Corp.").assertIsDisplayed()
        composeTestRule.onNodeWithText("$875.40").assertIsDisplayed()
    }

    @Test
    fun clickOnStockTriggersCallback() {
        var clickedSymbol = ""
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("AAPL", "Apple Inc.", "$178.50", PriceChange.NONE)
            )
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = { clickedSymbol = it },
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").performClick()
        assertEquals("AAPL", clickedSymbol)
    }

    @Test
    fun toggleFeedButtonTriggersCallback() {
        var toggled = false
        val state = FeedScreenState(isFeedActive = false)
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = { toggled = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Start").performClick()
        assertTrue(toggled)
    }

    @Test
    fun emptyStockListShowsNoItems() {
        val state = FeedScreenState(stocks = persistentListOf())
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("AAPL").assertDoesNotExist()
        composeTestRule.onNodeWithText("Price Tracker").assertIsDisplayed()
    }

    @Test
    fun displaysPriceChangeIndicators() {
        val state = FeedScreenState(
            stocks = persistentListOf(
                StockRowState("AAPL", "Apple", "$180.00", PriceChange.UP),
                StockRowState("GOOG", "Alphabet", "$140.00", PriceChange.DOWN),
                StockRowState("TSLA", "Tesla", "$245.00", PriceChange.NONE)
            )
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedScreenContent(
                    stateReader = { state },
                    onStockClick = {},
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }
}
