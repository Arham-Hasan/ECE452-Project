package com.example.tracktor.data.repository

interface UserRepository {
    suspend fun createUser(name: String, userId: String): Unit

    suspend fun getUserName(userId: String): String
}