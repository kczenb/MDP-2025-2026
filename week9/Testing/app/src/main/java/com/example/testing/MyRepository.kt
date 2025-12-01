package com.example.testing

class MyRepository {
    fun getData(): List<String> {
        TODO("Not yet implemented")
    }

    fun getUser(userId: String): UserProfile? {
        val data: String = userId
        return UserProfile(userId, "test", "test")
    }
}
