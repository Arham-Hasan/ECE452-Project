package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Inventory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): InventoryRepository{

    override suspend fun createInventory(id: String) {
        val inventory: Inventory =  Inventory(id = id)
        firestore.collection("inventory").document(id)
            .set(inventory)
    }
}