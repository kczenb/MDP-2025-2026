package com.example.testing

import android. content. Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Patterns
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ServiceTestRule
import com.google.firebase.firestore.auth.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

class DataValidator (private val context: Context){

    operator fun invoke(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}