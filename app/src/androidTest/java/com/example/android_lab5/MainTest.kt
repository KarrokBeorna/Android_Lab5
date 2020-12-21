package com.example.android_lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainTest {
    @get:Rule
    val activityRuleTask2 = ActivityScenarioRule(Activity1::class.java)


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
    fun backstackTestfull() {
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button4)).perform(click())
        first()
        pressBack()
        first()
        try {
            onView(withId(R.id.button2)).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            print("Нет у нас сейчас кнопки №2")
        }

        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        pressBack()
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        try {
            onView(withId(R.id.button2)).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            print("Нет у нас сейчас кнопки №2")
        }
    }

    @Test
    fun backstackTest() {
        first()
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        third()
        onView(withId(R.id.button4)).perform(click())
        first()
        pressBack()
        first()
        try {
            onView(withId(R.id.button2)).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            print("Нет у нас сейчас кнопки №2")
        }
    }

    @Test
    fun backstackTest2() {
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.button3)).perform(click())
        onView(withId(R.id.button5)).perform(click())
        onView(withId(R.id.button2)).perform(click())
        pressBack()
        //android.os.Process.killProcess(android.os.Process.myPid())
        onView(withId(R.id.button)).check(matches(isDisplayed()))
        try {
            onView(withId(R.id.button2)).check(matches(isDisplayed()))
        } catch (e: NoMatchingViewException) {
            print("Нет у нас сейчас кнопки №2")
        }
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
        backstackTest2()
    }
}