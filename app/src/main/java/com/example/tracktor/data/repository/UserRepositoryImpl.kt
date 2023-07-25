package com.example.tracktor.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    UserRepository {
    override suspend fun createUser(name: String, userId: String) {
        val name = hashMapOf(
            "name" to name,
        )
        println("userid: $userId ")
        firestore.collection("users").document(userId)
            .set(name)
    }

    override suspend fun getUserName(userId: String): String {

        val result = firestore.collection("users").document(userId).get().await()
        return result.getString("name")!!
    }
}