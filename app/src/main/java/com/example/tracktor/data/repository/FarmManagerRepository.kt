package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.example.tracktor.data.model.UserTransaction

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
    suspend fun addInventoryItem(itemName : String, itemPrice: Double): Unit
    suspend fun getInventoryItems(): List<String>?
    suspend fun addPickTransaction(itemName: String, userTransaction: UserTransaction):Unit
    suspend fun addSellTransaction(itemName: String, userTransaction: UserTransaction):Unit
    suspend fun getJoinRequests() : List<FarmUserRelation>?
    suspend fun getFarmUsers() : List<FarmUserRelation>?
    suspend fun toggleAdmin(userId: String) : Unit

}