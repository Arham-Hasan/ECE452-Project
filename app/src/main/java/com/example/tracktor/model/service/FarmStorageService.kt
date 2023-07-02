package com.example.tracktor.model.service

import com.example.tracktor.model.Farm

interface FarmStorageService  {
    suspend fun getFarmsFromUserId(userId: String) : List<Farm?>
    suspend fun createFarm(name: String, userId: String) : Unit
    suspend fun deleteFarm(farmId: String, userId: String) : Unit
    suspend fun requestToJoinFarm(farmId: String, userId: String) : Unit
    suspend fun acceptRequestToJoinFarm(farmId: String, userId: String) : Unit
    suspend fun deleteRequestToJoinFarm(farmId: String, userId: String) : Unit

}