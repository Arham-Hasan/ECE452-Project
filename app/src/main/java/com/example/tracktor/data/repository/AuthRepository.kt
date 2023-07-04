package com.example.tracktor.data.repository

import com.example.tracktor.data.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val loggedIn: Boolean
    val currentUserId: String

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun signOut()
    suspend fun signUp(email: String, password: String)
}