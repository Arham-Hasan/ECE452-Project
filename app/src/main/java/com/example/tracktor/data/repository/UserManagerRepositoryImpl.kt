package com.example.tracktor.data.repository

import com.example.tracktor.data.model.User
import javax.inject.Inject

class UserManagerRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    ): UserManagerRepository{

    override val loggedIn: Boolean
        get() = authRepository.loggedIn

    override val currentUserId: String
        get() = authRepository.currentUserId

    override suspend fun authenticate(email: String, password: String) = authRepository.authenticate(email, password)

    override suspend fun signOut() = authRepository.signOut()

    override suspend fun getCurrentUser(): User {
        val currentUserId = authRepository.currentUserId
        val userName = userRepository.getUserName(currentUserId)
        return User(currentUserId,userName)
    }

    override suspend fun signUp(name:String, email: String, password: String) {
        authRepository.signUp(email, password)
        while(currentUserId.isEmpty()){}
        userRepository.createUser(name, currentUserId)

    }

    override suspend fun getUserName(userId: String): String {
        return userRepository.getUserName(userId)
    }

}