package com.example.tracktor.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): InventoryRepository{

    override suspend fun addItem(name: String, farmId: String) {
        val newItem = hashMapOf(
            name to UUID.randomUUID().toString(),
        )
        val result = firestore.collection("farms")
            .whereEqualTo("farmId", farmId)
            .get().await()

        result.documents[0].reference.set(newItem, SetOptions.merge())
    }
}