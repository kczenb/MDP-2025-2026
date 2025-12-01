package com.example.testing

import org.junit.Assert.*
import org.junit.Test

class UserLoginValidatorTest {

    @Test
    fun `validate user correct name, returns true` () {
        val isValid = UserLoginValidator().invoke("Nabil")

        assertTrue(isValid)
    }

    @Test
    fun `validate empty name, returns false` () {
        val isValid = UserLoginValidator().invoke("")
        assertFalse(isValid)

    }


}