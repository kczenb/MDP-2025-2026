package com.example.post_manager_app.repository

import com.example.post_manager_app.data.Post

class FakePostRepository: IPostRepository {
    override suspend fun getPosts(): List<Post> {
        return listOf(
            Post(1, 1, "Fake Title 1", "Fake Body 1"),
            Post(1, 2, "Fake Title 2", "Fake Body 2")
        )
    }
}