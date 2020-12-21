package com.example.android_lab5

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
class EspressoTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActTest() {
        val button = onView(withId(R.id.button))
        val editText = onView(withId(R.id.editText))

        editText.check(matches(withText("Hello World!")))
        button.check(matches(withText("Button")))

        button.perform(click()).check(matches(withText("Ну, мы нажали...")))
        editText.perform(replaceText("Привет, мир!"))

        activityRule.scenario.onActivity{
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        editText.check(matches(withText("Привет, мир!")))
        button.check(matches(withText("Button")))
    }
}