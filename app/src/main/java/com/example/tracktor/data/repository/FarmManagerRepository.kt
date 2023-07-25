package com.example.tracktor.data.repository

import android.net.Uri
import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.example.tracktor.data.model.Inventory
import com.example.tracktor.data.model.SellTransaction
import com.example.tracktor.data.model.UserTransaction

interface FarmManagerRepository {

    fun changeSelectedFarm(farm: Farm): Unit
    fun removeSelectedFarm(): Unit
    fun getSelectedFarm(): Farm?
    suspend fun getActiveFarms(): List<Farm?>
    suspend fun createFarm(name: String): Unit
    suspend fun deleteFarm(): Unit
    suspend fun requestToJoinFarm(farmId: String): Unit
    suspend fun acceptUserRequest(userId: String): Unit
    suspend fun declineUserRequest(userId: String): Unit
    suspend fun isAdmin(): Boolean
    suspend fun addInventoryItem(itemName: String, itemPrice: Double, imageUri: Uri?): Unit
    suspend fun uploadInventoryItemImage(itemName: String, imageUri: Uri?): Unit
    suspend fun getInventoryItemImage(itemName: String): Any?
    suspend fun getInventoryItemNames(): List<String>?
    suspend fun getInventoryItemNamesToPrice(): Map<String, Double>
    suspend fun getInventoryItemNamesToQuantity(): Map<String, Long>
    suspend fun addPickTransaction(itemName: String, userTransaction: UserTransaction): Unit
    suspend fun addSellTransaction(itemName: String, userTransaction: SellTransaction): Unit
    suspend fun getJoinRequests(): List<FarmUserRelation>?
    suspend fun getFarmUsers(): List<FarmUserRelation>?
    suspend fun toggleAdmin(userId: String): Unit
    suspend fun changeFarmName(newName: String): Unit
    suspend fun getInventory(): Inventory

}