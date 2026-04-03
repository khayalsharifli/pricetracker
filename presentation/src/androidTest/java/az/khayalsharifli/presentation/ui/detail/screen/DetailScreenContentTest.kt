package az.khayalsharifli.presentation.ui.detail.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.detail.mvi.DetailScreenState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysSymbolInTopBar() {
        val state = DetailScreenState(symbol = "NVDA", name = "NVIDIA Corp.")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("NVDA").assertIsDisplayed()
    }

    @Test
    fun displaysStockName() {
        val state = DetailScreenState(name = "NVIDIA Corp.")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("NVIDIA Corp.").assertIsDisplayed()
    }

    @Test
    fun displaysPrice() {
        val state = DetailScreenState(formattedPrice = "$875.40")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("$875.40").assertIsDisplayed()
    }

    @Test
    fun displaysDescription() {
        val state = DetailScreenState(description = "GPU computing platform")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("GPU computing platform").assertIsDisplayed()
    }

    @Test
    fun displaysAboutSection() {
        val state = DetailScreenState(description = "Some description")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("About").assertIsDisplayed()
    }

    @Test
    fun displaysBackButton() {
        val state = DetailScreenState.INITIAL
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }

    @Test
    fun backButtonTriggersCallback() {
        var backClicked = false
        val state = DetailScreenState.INITIAL
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = { backClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assertTrue(backClicked)
    }

    @Test
    fun displaysUpArrowForPriceIncrease() {
        val state = DetailScreenState(
            formattedPrice = "$200.00",
            priceChange = PriceChange.UP
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }

    @Test
    fun displaysDownArrowForPriceDecrease() {
        val state = DetailScreenState(
            formattedPrice = "$100.00",
            priceChange = PriceChange.DOWN
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }

    @Test
    fun displaysAllDetailsTogether() {
        val state = DetailScreenState(
            symbol = "TSLA",
            name = "Tesla Inc.",
            formattedPrice = "$245.80",
            priceChange = PriceChange.DOWN,
            description = "Electric vehicles and energy storage"
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("TSLA").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tesla Inc.").assertIsDisplayed()
        composeTestRule.onNodeWithText("$245.80").assertIsDisplayed()
        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
        composeTestRule.onNodeWithText("Electric vehicles and energy storage").assertIsDisplayed()
    }

    @Test
    fun handlesInitialEmptyState() {
        val state = DetailScreenState.INITIAL
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailScreenContent(
                    stateReader = { state },
                    onBackClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("$0.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed()
    }
}
