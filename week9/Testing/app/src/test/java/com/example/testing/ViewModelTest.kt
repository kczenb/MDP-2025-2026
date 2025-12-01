package com.example.testing

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test



class ViewModelTest {
    private val repository = mockk<MyRepository>()
    private val viewModel = MyViewModel(repository)

    @Test
    fun fetchData_callsRepository() {
        every { repository.getData() } returns listOf("Item1", "Item2")

        viewModel.fetchData()

        verify { repository.getData() }
    }

    @Test
    fun `loadUserProfile returns user from repository`() {
        // Arrange (Stub the behavior)
        val expectedUser = UserProfile("123", "John Doe")
        every { repository.getUser("123") } returns expectedUser

        // Act
        val result = viewModel.loadUserProfile("123")

        // Assert
        assertEquals(expectedUser, result)
    }
}

