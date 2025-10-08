package com.example.post_manager_app.network

import com.example.post_manager_app.data.Post
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

}