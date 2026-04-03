package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Rule
import org.junit.Test

class PriceChangeIndicatorComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysUpArrowForPriceUp() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceChangeIndicatorComposable(priceChange = PriceChange.UP)
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }

    @Test
    fun displaysDownArrowForPriceDown() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceChangeIndicatorComposable(priceChange = PriceChange.DOWN)
            }
        }

        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }

    @Test
    fun displaysDashForPriceNone() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                PriceChangeIndicatorComposable(priceChange = PriceChange.NONE)
            }
        }

        composeTestRule.onNodeWithText("-").assertIsDisplayed()
    }
}
