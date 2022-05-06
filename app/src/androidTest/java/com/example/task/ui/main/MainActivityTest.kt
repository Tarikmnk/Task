package com.example.task.ui.main

import android.content.Intent
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.task.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.task.utils.ClickOnButtonView
import com.example.task.utils.atPositionOnView

import org.hamcrest.Description
import org.hamcrest.Matchers.not

/**
 * Created by Tarik MANKAOGLU on 6.05.2022.
 */

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var senerio: ActivityScenario<MainActivity>

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        rule.inject()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        senerio = ActivityScenario.launch(Intent(appContext, MainActivity::class.java))
        senerio.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testUI() {
        val latch = CountDownLatch(3)
        Espresso.onView(withId(R.id.rvMatches)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ClickOnButtonView(R.id.btnBet)
            )
        )

        Espresso.onView(withId(R.id.tvPrediction1)).perform(ClickOnButtonView(R.id.btnPlus))
        Espresso.onView(withId(R.id.tvPrediction2)).perform(ClickOnButtonView(R.id.btnPlus))

        Espresso.onView(withId(R.id.btnOkay)).perform(click())
        latch.await(2, TimeUnit.SECONDS)
        Espresso.onView(withId(R.id.rvMatches))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPositionOnView(0, withText("1 : 1"), R.id.tvBet)))

        Espresso.onView(withId(R.id.btnResult)).perform(click())

        latch.await(2, TimeUnit.SECONDS)
        Espresso.onView(withId(R.id.rvResult))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPositionOnView(0, withText("1 : 1"), R.id.tvBet)))

        Espresso.onView(withId(R.id.btnRestart)).perform(click())
        latch.await(2, TimeUnit.SECONDS)
        Espresso.onView(withId(R.id.rvMatches))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPositionOnView(0, not(isDisplayed()), R.id.tvBet)))
    }
}
