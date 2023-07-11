package com.example.tracktor.data.repository

import com.example.tracktor.data.model.User

interface UserRepository {
    suspend fun createUser(name: String, userId: String) : Unit

    suspend fun getUserName(userId:String): String
}