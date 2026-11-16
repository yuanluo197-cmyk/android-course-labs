package com.example.lab04

import android.content.pm.ActivityInfo
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameUiTest {
    @get:Rule val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before fun reset() {
        rule.scenario.onActivity { (it.application as PlayerApp).player = Player("罗元", "2024110827") }
        rule.scenario.recreate()
    }

    @Test fun aboutDialogFromMenu() {
        onView(withId(R.id.menuButton)).perform(click())
        onView(withText(R.string.about_title)).perform(click())
        onView(withText(R.string.about_message)).check(matches(isDisplayed()))
    }

    @Test fun flipThreeAndRestart() {
        onView(withId(R.id.play)).perform(click())
        onView(withId(R.id.cards)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(withText(containsString("/ 8"))))
        onView(withContentDescription("第1张，未翻开")).perform(click())
        onView(withContentDescription("第2张，未翻开")).perform(click())
        onView(withContentDescription("第3张，未翻开")).perform(click())
        onView(withId(R.id.score)).check(matches(withText(containsString("得分："))))
        onView(withId(R.id.restart)).perform(click())
        onView(withId(R.id.score)).check(matches(withText(containsString("得分：0"))))
    }

    @Test fun rotateChangesBoardSize() {
        ActivityScenario.launch(GameActivity::class.java).use { scenario ->
            scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }
            Thread.sleep(1200)
            onView(withId(R.id.score)).check(matches(withText(containsString("/ 6"))))
            scenario.onActivity { it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
            Thread.sleep(1200)
            onView(withId(R.id.score)).check(matches(withText(containsString("/ 8"))))
        }
    }

    @Test fun saveProfile() {
        onView(withId(R.id.edit)).perform(click())
        onView(withId(R.id.name)).perform(replaceText("罗元测试"), closeSoftKeyboard())
        onView(withId(R.id.save)).perform(scrollTo(), click())
        onView(withId(R.id.profile)).check(matches(withText(containsString("罗元测试"))))
    }
}
