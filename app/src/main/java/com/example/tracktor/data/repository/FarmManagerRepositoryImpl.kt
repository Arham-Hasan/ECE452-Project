package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
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
            farmUserRepository.requestToJoinFarm(userId = currentUserId, farmId = farmId)
        }
    }

    override suspend fun isAdmin(): Boolean {
        if(currentFarm == null){
            return false
        }
        val currentUserId = authRepository.currentUserId
        return farmUserRepository.isAdmin(currentUserId,currentFarm!!)
    }
}