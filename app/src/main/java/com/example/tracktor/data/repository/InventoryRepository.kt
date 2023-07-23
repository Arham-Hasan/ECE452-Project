package com.example.tracktor.data.repository

import android.net.Uri
import com.example.tracktor.data.model.InventoryItem
import com.example.tracktor.data.model.SellTransaction
import com.example.tracktor.data.model.UserTransaction
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Transaction

interface InventoryRepository {
    suspend fun createInventory(id: String) : Unit
    suspend fun getInventoryById(id: String) : DocumentSnapshot
    suspend fun itemExists(name : String, inventoryId: String) : Boolean
    suspend fun addItem(name : String, inventoryId: String, itemPrice: Double, imageRef : String?) : Unit
    suspend fun userStatExistsForItem(itemName : String, userId : String, inventoryId: String) : Boolean
    suspend fun addUserStatForItem(itemName : String, userId : String, inventoryId: String) : Unit
    suspend fun addPickTransaction(pickTransaction: UserTransaction, itemName: String,userId: String, inventoryId: String) : Unit
    suspend fun addSellTransaction(sellTransaction: SellTransaction, itemName: String,userId: String, inventoryId: String) : Unit
    suspend fun getItemNames(inventoryId: String) : List<String>?
    suspend fun getItemNameToPrice(inventoryId: String) : Map<String, Double>
    suspend fun getInventoryItemNamesToQuantity(inventoryId: String): Map<String, Long>
}