package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.InventoryItem

interface FarmManagerRepository {

    fun changeSelectedFarm(farm:Farm): Unit
    fun removeSelectedFarm(): Unit
    fun getSelectedFarm(): Farm?
    suspend fun getActiveFarms() : List<Farm?>
    suspend fun createFarm(name: String) : Unit
    suspend fun deleteFarm(): Unit
    suspend fun requestToJoinFarm(farmId: String): Unit
    suspend fun acceptUserRequest(userId:String): Unit
    suspend fun declineUserRequest(userId: String): Unit
    suspend fun isAdmin():Boolean
    suspend fun addInventoryItem(itemName : String): Unit
    suspend fun getInventoryItems(): List<String>?
}