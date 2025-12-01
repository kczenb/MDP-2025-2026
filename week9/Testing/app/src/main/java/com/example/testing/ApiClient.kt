package com.example.testing

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

data class ApiResponse(val success: Boolean)

interface ApiService {
    @GET("/data")
    suspend fun getDataFromServer(): ApiResponse
}

class ApiClient(baseUrl: String) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getDataFromServer(): ApiResponse {
        return apiService.getDataFromServer()
    }
}