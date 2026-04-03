package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import az.khayalsharifli.presentation.ui.feed.mvi.ConnectionIndicatorState
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Rule
import org.junit.Test

class ConnectionIndicatorComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysLiveLabelWhenConnected() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                ConnectionIndicatorComposable(
                    stateReader = {
                        ConnectionIndicatorState(
                            isConnected = true,
                            isConnecting = false,
                            label = "Live"
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Live").assertIsDisplayed()
    }

    @Test
    fun displaysOfflineLabelWhenDisconnected() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                ConnectionIndicatorComposable(
                    stateReader = {
                        ConnectionIndicatorState(
                            isConnected = false,
                            isConnecting = false,
                            label = "Offline"
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Offline").assertIsDisplayed()
    }

    @Test
    fun displaysConnectingLabelWhenConnecting() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                ConnectionIndicatorComposable(
                    stateReader = {
                        ConnectionIndicatorState(
                            isConnected = false,
                            isConnecting = true,
                            label = "Connecting..."
                        )
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Connecting...").assertIsDisplayed()
    }
}
