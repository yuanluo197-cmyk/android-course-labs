package com.example.lab04
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
 @get:Rule val rule=ActivityScenarioRule(MainActivity::class.java)
 private fun screenshot(name:String){
  val inst=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation()
  inst.waitForIdleSync()
  val bitmap=inst.uiAutomation.takeScreenshot()
  val file=java.io.File(inst.targetContext.getExternalFilesDir(null),name)
  java.io.FileOutputStream(file).use { bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG,100,it) }
 }
 @Before fun reset(){rule.scenario.onActivity{(it.application as PlayerApp).player=Player("罗元","2024110827")};rule.scenario.recreate()}
 @Test fun saveProfile(){screenshot("01-main.png");onView(withId(R.id.edit)).perform(click());onView(withId(R.id.name)).perform(replaceText("罗元测试"),closeSoftKeyboard());screenshot("02-edit.png");onView(withId(R.id.save)).perform(scrollTo(),click());onView(withId(R.id.profile)).check(matches(withText(containsString("罗元测试"))))}
 @Test fun cancelProfile(){onView(withId(R.id.edit)).perform(click());onView(withId(R.id.name)).perform(replaceText("取消修改"),closeSoftKeyboard());onView(withId(R.id.cancel)).perform(scrollTo(),click());onView(withId(R.id.profile)).check(matches(withText(containsString("姓名：罗元"))))}
 @Test fun flipAndRestart(){onView(withId(R.id.play)).perform(click());screenshot("03-game.png");onView(withContentDescription("第1张，未翻开")).perform(click());onView(withId(R.id.score)).check(matches(withText(containsString("-1"))));screenshot("04-flipped.png");onView(withId(R.id.restart)).perform(scrollTo(),click());onView(withId(R.id.score)).check(matches(withText(containsString("得分：0"))))}
 @Test fun recreateKeepsGame(){ActivityScenario.launch(GameActivity::class.java).use { scenario -> onView(withContentDescription("第1张，未翻开")).perform(click());scenario.recreate();onView(withId(R.id.score)).check(matches(withText(containsString("-1")))) }}
}
