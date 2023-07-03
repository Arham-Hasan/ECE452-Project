package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import java.util.UUID
import javax.inject.Inject

class FarmManagerRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val farmRepository: FarmRepository,
    private val farmUserRepository: FarmUserRepository,
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


    override suspend fun getFarms() : List<Farm?>{
        val farmIds = farmUserRepository.getFarmIds(authRepository.currentUserId)
        return farmRepository.getFarms(farmIds)
    }
    override suspend fun createFarm(name: String) : Unit{
        val farm = Farm(name = name, id = UUID.randomUUID().toString() )
        val currentUserId = authRepository.currentUserId
        farmRepository.createFarm(farm)
        farmUserRepository.createFarm(farm, currentUserId)
    }
}