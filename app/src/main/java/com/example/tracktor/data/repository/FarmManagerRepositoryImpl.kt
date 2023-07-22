package com.example.tracktor.data.repository

import android.util.Log
import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.example.tracktor.data.model.UserTransaction
import java.util.UUID
import javax.inject.Inject

class FarmManagerRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val farmRepository: FarmRepository,
    private val farmUserRepository: FarmUserRepository,
    private val inventoryRepository: InventoryRepository,
    ): FarmManagerRepository {

    private var currentFarm:Farm?= null
    override fun changeSelectedFarm(farm: Farm) {
        currentFarm = farm
    }

    override fun removeSelectedFarm() {
        currentFarm= null
    }

    override fun getSelectedFarm(): Farm? {
        return currentFarm
    }


    override suspend fun getActiveFarms() : List<Farm?>{
        val farmIds = farmUserRepository.getActiveFarmIds(authRepository.currentUserId)
        return farmRepository.getFarms(farmIds)
    }
    override suspend fun createFarm(name: String) : Unit{
        val inventoryId = UUID.randomUUID().toString()
        val farm = Farm(name = name, id = UUID.randomUUID().toString(), inventoryId = inventoryId)
        val currentUserId = authRepository.currentUserId
        farmRepository.createFarm(farm)
        farmUserRepository.createFarm(farm, currentUserId)
        inventoryRepository.createInventory(inventoryId)
    }

    override suspend fun deleteFarm() {
        val currentUserId = authRepository.currentUserId
        val farm = currentFarm!!
        if(farmUserRepository.isAdmin(currentUserId, farm)){
            farmRepository.deleteFarm(farm)
            farmUserRepository.deleteFarm(farm)
        }
    }

    override suspend fun requestToJoinFarm(farmId: String) {
        val currentUserId = authRepository.currentUserId
        if(farmRepository.farmExists(farmId) && !farmUserRepository.isActiveOrNonActiveFarmMember(currentUserId,farmId)){
            farmUserRepository.addNonActiveUserToFarm(userId = currentUserId, farmId = farmId)
        }
    }

    override suspend fun isAdmin(): Boolean {
        if(currentFarm == null){
            return false
        }
        val currentUserId = authRepository.currentUserId
        return farmUserRepository.isAdmin(currentUserId,currentFarm!!)
    }

    override suspend fun acceptUserRequest(userId:String) {
        if(currentFarm == null)return
        farmUserRepository.setUserToActive(userId = userId, farmId = currentFarm!!.id)
    }

    override suspend fun declineUserRequest(userId: String) {
        if(currentFarm == null)return
        farmUserRepository.deleteFarmUserRelation(userId = userId, farmId = currentFarm!!.id)
    }

    override suspend fun addInventoryItem(itemName: String) {
        inventoryRepository.addItem(name = itemName, inventoryId = currentFarm!!.inventoryId)
    }

    override suspend fun getInventoryItems(): List<String>? {
        return inventoryRepository.getItems(inventoryId = currentFarm!!.inventoryId)
    }

    override suspend fun addPickTransaction(itemName: String, userTransaction: UserTransaction) {
        if(inventoryRepository.itemExists(inventoryId = currentFarm!!.inventoryId, name = itemName)){
            if(!inventoryRepository.userStatExistsForItem(itemName = itemName, userId = authRepository.currentUserId,
                inventoryId = currentFarm!!.inventoryId)){
                inventoryRepository.addUserStatForItem(itemName=itemName, userId = authRepository.currentUserId,
                    inventoryId = currentFarm!!.inventoryId)
            }
            inventoryRepository.addPickTransaction(itemName = itemName, pickTransaction = userTransaction,
                inventoryId = currentFarm!!.inventoryId, userId = authRepository.currentUserId)
        }
    }

    override suspend fun addSellTransaction(itemName: String, userTransaction: UserTransaction) {
        if(inventoryRepository.itemExists(inventoryId = currentFarm!!.inventoryId, name = itemName)){
            if(!inventoryRepository.userStatExistsForItem(itemName = itemName, userId = authRepository.currentUserId,
                    inventoryId = currentFarm!!.inventoryId)){
                inventoryRepository.addUserStatForItem(itemName=itemName, userId = authRepository.currentUserId,
                    inventoryId = currentFarm!!.inventoryId)
            }
            inventoryRepository.addSellTransaction(itemName = itemName, sellTransaction = userTransaction,
                inventoryId = currentFarm!!.inventoryId, userId = authRepository.currentUserId)
        }
    }

    override suspend fun getJoinRequests(): List<FarmUserRelation>? {
        return farmUserRepository.getNonActiveUsers(farmId = currentFarm!!.id)
    }

    override suspend fun getFarmUsers(): List<FarmUserRelation>? {
        return farmUserRepository.getActiveUsers(farmId = currentFarm!!.id)
    }

    override suspend fun changeFarmName(newName: String) {
        val currentUserId = authRepository.currentUserId
        val farm = currentFarm!!
        if(farmUserRepository.isAdmin(currentUserId, farm)){
            farmRepository.changeFarmName(newName,farm)
        }
    }
}