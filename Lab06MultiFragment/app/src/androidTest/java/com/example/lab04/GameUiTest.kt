package com.example.lab04

import androidx.fragment.app.FragmentActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameUiTest {
    @get:Rule val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test fun tabsShowSeparateFragments() {
        assertFragment(ProfileFragment::class.java)
        onView(withId(R.id.pair_tab)).perform(click())
        assertFragment(PairGameFragment::class.java)
        onView(withId(R.id.score)).check(matches(withText(containsString("/ 8"))))
        onView(withId(R.id.triple_tab)).perform(click())
        assertFragment(TripleGameFragment::class.java)
        rule.scenario.recreate()
        assertFragment(TripleGameFragment::class.java)
        onView(withId(R.id.profile_tab)).perform(click())
        assertFragment(ProfileFragment::class.java)
    }

    private fun assertFragment(type: Class<*>) {
        rule.scenario.onActivity { activity ->
            val fragment = (activity as FragmentActivity).supportFragmentManager.findFragmentById(R.id.content)
            assertTrue(type.isInstance(fragment))
        }
    }
}
