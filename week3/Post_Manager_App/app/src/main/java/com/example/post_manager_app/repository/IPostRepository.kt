package com.example.post_manager_app.repository

import com.example.post_manager_app.data.Post

interface IPostRepository {
    suspend fun getPosts(): List<Post>
}