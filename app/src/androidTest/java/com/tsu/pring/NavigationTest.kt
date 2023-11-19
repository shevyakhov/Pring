package com.tsu.pring

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tsu.pring.application.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun testNavigation() {
        // Нажатие на элемент нижней навигации с id R.id.home
        Espresso.onView(ViewMatchers.withId(R.id.home)).perform(ViewActions.click());

        // Нажатие на элемент нижней навигации с id R.id.stats
        Espresso.onView(ViewMatchers.withId(R.id.stats)).perform(ViewActions.click());

        // Нажатие на элемент нижней навигации с id R.id.prediction
        Espresso.onView(ViewMatchers.withId(R.id.prediction)).perform(ViewActions.click());
    }

}


