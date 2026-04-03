package az.khayalsharifli.presentation.ui.detail.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Rule
import org.junit.Test

class PriceCardComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysFormattedPrice() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$875.40",
                    priceChange = PriceChange.NONE
                )
            }
        }

        composeTestRule.onNodeWithText("$875.40").assertIsDisplayed()
    }

    @Test
    fun displaysUpArrowWhenPriceUp() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$200.00",
                    priceChange = PriceChange.UP
                )
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }

    @Test
    fun displaysDownArrowWhenPriceDown() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$100.00",
                    priceChange = PriceChange.DOWN
                )
            }
        }

        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }

    @Test
    fun displaysDashWhenPriceNone() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$150.00",
                    priceChange = PriceChange.NONE
                )
            }
        }

        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }

    @Test
    fun displaysZeroPrice() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$0.00",
                    priceChange = PriceChange.NONE
                )
            }
        }

        composeTestRule.onNodeWithText("$0.00").assertIsDisplayed()
    }

    @Test
    fun displaysLargePrice() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceCardComposable(
                    formattedPrice = "$99999.99",
                    priceChange = PriceChange.UP
                )
            }
        }

        composeTestRule.onNodeWithText("$99999.99").assertIsDisplayed()
    }
}
