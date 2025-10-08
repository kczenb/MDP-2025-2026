package com.example.post_manager_app.repository

import com.example.post_manager_app.data.Post
import com.example.post_manager_app.network.JsonPlaceholderApi

class PostRepository(private val api: JsonPlaceholderApi) : IPostRepository {

    override suspend fun getPosts(): List<Post> {
        // We try to make an API call
        val response = api.getPosts()
        if (response.isSuccessful) {
            // If successful the body will contain the posts data
            return response.body() ?: emptyList()
        } else {
            throw Exception("Failed to fetch posts: ${response.code()}")
        }
    }
}