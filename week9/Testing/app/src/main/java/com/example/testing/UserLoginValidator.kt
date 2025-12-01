package com.example.testing

class UserLoginValidator {

    private val users = listOf("user1", "user2", "user3", "user4")

    operator fun invoke(user: String): Boolean {
        if (user.isEmpty()){
            return false
        }

        return true
    }
}