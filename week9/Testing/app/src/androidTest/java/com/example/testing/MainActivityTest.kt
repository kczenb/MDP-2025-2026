package com.example.testing

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import org.junit.After


import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun teardown() {
        Intents.release()
    }

    @Test
    fun btnLoginTest(){
        val username = "user"
        val password = "pass"


        onView(withId(R.id.et_username)).perform(clearText(), typeText(username), closeSoftKeyboard())
        onView(withId(R.id.et_password)).perform(clearText(), typeText(password), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        Intents.intended(hasComponent(SecondActivity::class.java.name))

    }
}