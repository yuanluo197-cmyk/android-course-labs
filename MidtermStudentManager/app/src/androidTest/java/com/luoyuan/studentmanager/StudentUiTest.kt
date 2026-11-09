package com.luoyuan.studentmanager

import android.graphics.Bitmap
import android.widget.TextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StudentUiTest {
    @get:Rule val rule = ActivityScenarioRule(MainActivity::class.java)

    private fun screenshot(name: String) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.waitForIdleSync()
        val bitmap = instrumentation.uiAutomation.takeScreenshot()
        val file = java.io.File(instrumentation.targetContext.getExternalFilesDir(null), name)
        java.io.FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    }

    private fun enterStudent(id: String = "2024110801") {
        onView(withId(R.id.studentId)).perform(replaceText(id), closeSoftKeyboard())
        onView(withId(R.id.studentName)).perform(replaceText("测试学生"), closeSoftKeyboard())
        onView(withId(R.id.male)).perform(click())
        onView(withId(R.id.className)).perform(replaceText("2024级2班"), closeSoftKeyboard())
    }

    @Test fun addReturnsStudentAndRefreshesList() {
        screenshot("01-student-list.png")
        onView(withId(R.id.addStudent)).perform(click())
        enterStudent()
        screenshot("02-add-student.png")
        onView(withId(R.id.confirm)).perform(scrollTo(), click())
        onView(withId(R.id.count)).check(matches(withText("共 2 位学生")))
        onView(withText("测试学生")).check(matches(isDisplayed()))
        screenshot("03-added-result.png")
    }

    @Test fun cancelDoesNotAddStudent() {
        onView(withId(R.id.addStudent)).perform(click())
        enterStudent()
        onView(withId(R.id.cancel)).perform(scrollTo(), click())
        onView(withId(R.id.count)).check(matches(withText("共 1 位学生")))
    }

    @Test fun emptyAndDuplicateInputAreRejected() {
        onView(withId(R.id.addStudent)).perform(click())
        onView(withId(R.id.confirm)).perform(scrollTo(), click())
        onView(withId(R.id.error)).check(matches(withText("请填写学号")))
        enterStudent("2024110827")
        onView(withId(R.id.confirm)).perform(scrollTo(), click())
        onView(withId(R.id.error)).check(matches(withText(containsString("该学号已存在"))))
        screenshot("04-validation.png")
    }

    @Test fun recreatedListKeepsAddedStudent() {
        onView(withId(R.id.addStudent)).perform(click())
        enterStudent()
        onView(withId(R.id.confirm)).perform(scrollTo(), click())
        rule.scenario.recreate()
        onView(withId(R.id.count)).check(matches(withText("共 2 位学生")))
        onView(withText("测试学生")).check(matches(isDisplayed()))
    }

    @Test fun titleAndTextColorMeetStudentNumberRule() {
        onView(withId(R.id.appTitle)).check(matches(withText("罗元2024110827")))
        rule.scenario.onActivity { activity ->
            assertEquals(0xFF795548.toInt(), activity.findViewById<TextView>(R.id.appTitle).currentTextColor)
        }
    }
}
