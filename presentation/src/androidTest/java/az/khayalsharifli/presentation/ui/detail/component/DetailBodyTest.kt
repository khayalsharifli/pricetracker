package az.khayalsharifli.presentation.ui.detail.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import az.khayalsharifli.domain.model.PriceChange
import az.khayalsharifli.presentation.ui.detail.mvi.DetailScreenState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Rule
import org.junit.Test

class DetailBodyTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysStockName() {
        val state = createState(name = "NVIDIA Corp.")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("NVIDIA Corp.").assertIsDisplayed()
    }

    @Test
    fun displaysFormattedPrice() {
        val state = createState(formattedPrice = "$875.40")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("$875.40").assertIsDisplayed()
    }

    @Test
    fun displaysDescription() {
        val state = createState(description = "GPU-accelerated computing platform")
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("GPU-accelerated computing platform").assertIsDisplayed()
    }

    @Test
    fun displaysAboutSectionTitle() {
        val state = createState()
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("About").assertIsDisplayed()
    }

    @Test
    fun displaysUpArrowForPriceIncrease() {
        val state = createState(priceChange = PriceChange.UP)
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("\u2191").assertIsDisplayed()
    }

    @Test
    fun displaysDownArrowForPriceDecrease() {
        val state = createState(priceChange = PriceChange.DOWN)
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
    }

    @Test
    fun displaysAllFieldsTogether() {
        val state = DetailScreenState(
            symbol = "TSLA",
            name = "Tesla Inc.",
            formattedPrice = "$245.80",
            priceChange = PriceChange.DOWN,
            description = "Electric vehicles and energy storage"
        )
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("Tesla Inc.").assertIsDisplayed()
        composeTestRule.onNodeWithText("$245.80").assertIsDisplayed()
        composeTestRule.onNodeWithText("\u2193").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
        composeTestRule.onNodeWithText("Electric vehicles and energy storage").assertIsDisplayed()
    }

    @Test
    fun handlesEmptyState() {
        val state = DetailScreenState.INITIAL
        composeTestRule.setContent {
            PriceTrackerTheme {
                DetailBody(stateReader = { state })
            }
        }

        composeTestRule.onNodeWithText("$0.00").assertIsDisplayed()
        composeTestRule.onNodeWithText("About").assertIsDisplayed()
    }

    private fun createState(
        symbol: String = "NVDA",
        name: String = "NVIDIA Corp.",
        formattedPrice: String = "$875.40",
        priceChange: PriceChange = PriceChange.NONE,
        description: String = "Test description"
    ) = DetailScreenState(
        symbol = symbol,
        name = name,
        formattedPrice = formattedPrice,
        priceChange = priceChange,
        description = description
    )
}
