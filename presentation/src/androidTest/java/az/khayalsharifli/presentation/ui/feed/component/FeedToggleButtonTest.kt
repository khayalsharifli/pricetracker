package az.khayalsharifli.presentation.ui.feed.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import az.khayalsharifli.presentation.ui.theme.PriceTrackerTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FeedToggleButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysStartWhenFeedInactive() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedToggleButton(
                    isFeedActive = false,
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Start").assertIsDisplayed()
    }

    @Test
    fun displaysStopWhenFeedActive() {
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedToggleButton(
                    isFeedActive = true,
                    onToggleFeed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Stop").assertIsDisplayed()
    }

    @Test
    fun clickTriggersOnToggleFeed() {
        var clicked = false
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedToggleButton(
                    isFeedActive = false,
                    onToggleFeed = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Start").performClick()
        assertTrue(clicked)
    }

    @Test
    fun clickTriggersOnToggleFeedWhenActive() {
        var clicked = false
        composeTestRule.setContent {
            PriceTrackerTheme {
                FeedToggleButton(
                    isFeedActive = true,
                    onToggleFeed = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Stop").performClick()
        assertTrue(clicked)
    }
}
