package com.example.testing

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class DataValidatorTest {

    private lateinit var dataValidator: DataValidator

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        dataValidator = DataValidator(context)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testValidEmails() {
        // Test cases for valid emails
        assertTrue(dataValidator.invoke("test@example.com"))
        assertTrue(dataValidator.invoke("user.name+tag+sorting@example.com"))
        assertTrue(dataValidator.invoke("valid_email@sub.domain.org"))
    }


}