package com.example.tracktor.data.repository

import com.example.tracktor.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserManagerRepository {
    val loggedIn: Boolean
    val currentUserId: String


    suspend fun authenticate(email: String, password: String)

    suspend fun signOut()

    suspend fun getCurrentUser(): User

    suspend fun signUp(name:String, email: String, password: String):Unit
}