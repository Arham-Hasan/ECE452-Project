package com.example.tracktor.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Transaction

interface InventoryRepository {
    suspend fun createInventory(id: String) : Unit
    suspend fun getInventoryById(id: String) : DocumentSnapshot
    suspend fun itemExists(name : String, inventoryId: String) : Boolean
    suspend fun addItem(name : String, inventoryId: String) : Unit
    suspend fun userStatExistsForItem(itemName : String, userId : String, inventoryId: String) : Boolean
    suspend fun addUserStatForItem(itemName : String, userId : String, inventoryId: String) : Unit
    suspend fun addPickTransaction(pickTransaction: Transaction, itemName: String,userId: String, inventoryId: String) : Unit
    suspend fun addSellTransaction(pickTransaction: Transaction, itemName: String,userId: String, inventoryId: String) : Unit


}