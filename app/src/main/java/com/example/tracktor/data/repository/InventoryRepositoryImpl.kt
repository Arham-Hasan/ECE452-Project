package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Inventory
import com.example.tracktor.data.model.InventoryItem
import com.example.tracktor.data.model.UserInventoryStat
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): InventoryRepository{

    override suspend fun createInventory(id: String) {
        val inventory: Inventory =  Inventory()
        firestore.collection("inventory").document(id)
            .set(inventory)
    }

    override suspend fun getInventoryById(id: String) : DocumentSnapshot {
        val result = firestore.collection("inventory").document(id).get().await()
        return result
    }

    override suspend fun itemExists(name: String, inventoryId: String): Boolean {
        val result = firestore.collection("inventory").document(inventoryId).get().await()
        val data = result.data
        if(data == null || !data.containsKey(name)){
            return false
        }
        return true
    }

    override suspend fun addItem(name: String, inventoryId: String) {
        val item = InventoryItem()
        firestore.collection("inventory").document(inventoryId)
            .set(mapOf(
                name to item,
            ), SetOptions.merge())
    }

    override suspend fun userStatExistsForItem(
        itemName: String,
        userId: String,
        inventoryId: String
    ): Boolean {
        val result = firestore.collection("inventory").document(inventoryId).get().await()
        val data = result.data
        val item = data?.get(itemName) as Map<*, *>
        if(item == null || !item.containsKey(userId)){
            return false
        }
        return true
    }

    override suspend fun addUserStatForItem(itemName: String, userId: String, inventoryId: String) {
        val map = mapOf(
            userId to UserInventoryStat()
        )

        val map2 = mapOf(
            itemName to map
        )
        firestore.collection("inventory").document(inventoryId).set(map2, SetOptions.merge())
    }

}