package com.example.testing


import org.junit.Test
import org.junit.jupiter.api.Assertions



class CalculatorTest {
    private val calculator = Calculator()

    @Test
    fun `addition works correctly`() {
        val result = calculator.add(2, 3)
        Assertions.assertEquals(5, result)
    }


}

