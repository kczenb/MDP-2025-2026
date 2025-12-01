package com.example.testing

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


class ApiTest {

    private lateinit var server: MockWebServer
    private lateinit var apiClient: ApiClient

    @Before
    fun setup() {
        // Initialize MockWebServer
        server = MockWebServer()
        server.start()

        // Initialize ApiClient with the server's URL
        apiClient = ApiClient(server.url("/").toString())
    }

    @After
    fun teardown() {
        // Shut down the server
        server.shutdown()
    }

    @Test
    fun testApi() = runBlocking {
        // Enqueue a mock response
        server.enqueue(MockResponse().setBody("{ \"success\": true }"))

        // Perform the API call
        val response = apiClient.getDataFromServer()

        // Verify the response
        assertTrue(response.success)
    }
}