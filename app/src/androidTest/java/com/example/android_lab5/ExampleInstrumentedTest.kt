package com.example.android_lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.android_lab5", appContext.packageName)
    }
}
@LargeTest
class EspressoTest {
    @get:Rule
    //val activityRule = ActivityScenarioRule(MainActivity::class.java)
    val activityRuleTask2 = ActivityScenarioRule(Activity1::class.java)

    /*@Test
    fun mainActTest() {
        val button = onView(withId(R.id.button))
        val editText = onView(withId(R.id.editText))

        editText.check(matches(withText("Hello World!")))
        button.check(matches(withText("Button")))

        button.perform(click()).check(matches(withText("Ну, мы нажали...")))
        editText.perform(replaceText("Привет, мир!"))

        activityRule.scenario.onActivity{
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        editText.check(matches(withText("Привет, мир!")))
        button.check(matches(withText("Button")))
    }*/

    private fun first() {
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        onView(withId(R.id.button2)).check(doesNotExist())
        onView(withId(R.id.button3)).check(doesNotExist())
        onView(withId(R.id.button4)).check(doesNotExist())
        onView(withId(R.id.button5)).check(doesNotExist())
    }

    private fun second() {
        onView(withId(R.id.button)).check(doesNotExist())
        onView(withId(R.id.button2)).check(matches(isDisplayed()))
        onView(withId(R.id.button3)).check(matches(isDisplayed()))
        onView(withId(R.id.button4)).check(doesNotExist())
        onView(withId(R.id.button5)).check(doesNotExist())
    }

    private fun third() {
        onView(withId(R.id.button)).check(doesNotExist())
        onView(withId(R.id.button2)).check(doesNotExist())
        onView(withId(R.id.button3)).check(doesNotExist())
        onView(withId(R.id.button4)).check(matches(isDisplayed()))
        onView(withId(R.id.button5)).check(matches(isDisplayed()))
    }

    @Test
    fun task2Test() {
        first()
        onView(withId(R.id.button)).perform(click())
        second()
        onView(withId(R.id.button2)).perform(click())
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button5)).perform(click())
        second()
        onView(withId(R.id.button2)).perform(click())
    }

    @Test
    fun backstackTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button4)).perform(click())
        first()
        pressBackUnconditionally()

        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        pressBackUnconditionally()
    }

    @Test
    fun landscapeTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        activityRuleTask2.scenario.onActivity{
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        second()
        onView(withId(R.id.button2)).perform(click())
        task2Test()
        backstackTest()
    }
}