package com.example.tracktor.model.service

import com.example.tracktor.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val loggedIn: Boolean
    val currentUserId: String

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun signOut()
    suspend fun signUp(email: String, password: String)
}