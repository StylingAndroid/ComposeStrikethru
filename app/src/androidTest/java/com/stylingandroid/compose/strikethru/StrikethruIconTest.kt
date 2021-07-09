package com.stylingandroid.compose.strikethru

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

class StrikethruIconTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    companion object {
        private const val TEST_TAG_NAME = "StrikethruIcon"

        @BeforeClass
        @JvmStatic
        fun clearExistingImagesBeforeStart() {
            clearExistingImages(TEST_TAG_NAME)
        }
    }

    @Test
    fun GivenAStrikethruIcon_WhenItHasDefaultStates_ThenTheStateHasNoStrikethru() {
        composeTestRule.setContent {
            StrikethruIcon()
        }

        assertScreenshotMatchesGolden(TEST_TAG_NAME, "no_strikethru", composeTestRule.onRoot())
    }

    @Test
    fun GivenAStrikethruIcon_WhenWeTapOnItOnce_ThenTheStateChangesToStrikethru() {
        composeTestRule.setContent {
            StrikethruIcon(modifier = Modifier.testTag(TEST_TAG_NAME))
        }

        composeTestRule.onNode(hasClickAction() and hasTestTag(TEST_TAG_NAME))
            .performClick()

        assertScreenshotMatchesGolden(TEST_TAG_NAME, "full_strikethru", composeTestRule.onRoot())
    }

    @Test
    fun GivenAStrikethruIcon_WhenWeTapOnItTwice_ThenTheStateChangesToNoStrikethru() {
        composeTestRule.setContent {
            StrikethruIcon(modifier = Modifier.testTag(TEST_TAG_NAME))
        }

        composeTestRule.onNode(hasClickAction() and hasTestTag(TEST_TAG_NAME))
            .performClick()
        composeTestRule.onNode(hasClickAction() and hasTestTag(TEST_TAG_NAME))
            .performClick()

        assertScreenshotMatchesGolden(TEST_TAG_NAME, "no_strikethru", composeTestRule.onRoot())
    }

    @Test
    fun GivenAStrikethruIcon_WhenWeTapOnItOnce_ThenTheStateIsCorrectAfter50ms() {
        composeTestRule.setContent {
            StrikethruIcon(modifier = Modifier.testTag(TEST_TAG_NAME))
        }

        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.onNode(hasClickAction() and hasTestTag("StrikethruIcon"))
            .performClick()
        composeTestRule.mainClock.advanceTimeBy(50)

        assertScreenshotMatchesGolden(TEST_TAG_NAME, "half_strikethru", composeTestRule.onRoot())
    }
}
