package com.example.android_lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainTest {
    @get:Rule
    val activityRuleTask2 = ActivityTestRule(Activity1::class.java)


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
    fun failedBackstackTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button4)).perform(click())
        first()
        ViewActions.pressBack()       // не бросает исключение...
        //pressBack()                 // бросает исключение, спасибочки ему
        //pressBackUnconditionally()  // не бросает исключение, отлично
    }

    @Test
    fun backstackTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button4)).perform(click())
        first()
        try {
            pressBack()
        } catch (e: NoActivityResumedException) {
            // приложение закрылось
        }
        assertTrue(activityRuleTask2.activity.isDestroyed)
    }

    @Test
    fun backstackTest2() {
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        try {
            pressBack()
        } catch (e: NoActivityResumedException) {
            // вышли из приложения
        }
        assertTrue(activityRuleTask2.activity.isDestroyed)
    }

    @Test
    fun landscapeTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        activityRuleTask2.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        second()
        onView(withId(R.id.button2)).perform(click())
        task2Test()
        backstackTest()
    }
}