package my.edu.nottingham.firebasetestlab_demo



import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class CalculatorTest {
private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAddition() {
        // Test 2 + 3 = 5
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_add)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())

        onView(withId(R.id.display)).check(matches(withText("5")))
    }

    @Test
    fun testSubtraction() {
        // Test 8 - 3 = 5
        onView(withId(R.id.btn_8)).perform(click())
        onView(withId(R.id.btn_subtract)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())

        onView(withId(R.id.display)).check(matches(withText("5")))
    }

    @Test
    fun testMultiplication() {
        // Test 4 × 3 = 12
        onView(withId(R.id.btn_4)).perform(click())
        onView(withId(R.id.btn_multiply)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())

        onView(withId(R.id.display)).check(matches(withText("12")))
    }

    @Test
    fun testClear() {
        // Test clear functionality
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_clear)).perform(click())

        onView(withId(R.id.display)).check(matches(withText("0")))
    }

    @Test
    fun testDecimalOperation() {
        // Test 2.5 + 1.5 = 4
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_decimal)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_add)).perform(click())
        onView(withId(R.id.btn_1)).perform(click())
        onView(withId(R.id.btn_decimal)).perform(click())
        onView(withId(R.id.btn_5)).perform(click())
        onView(withId(R.id.btn_equals)).perform(click())

        onView(withId(R.id.display)).check(matches(withText("4")))
    }
}