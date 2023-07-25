package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmRepository {
    suspend fun getFarms(farmIds: List<String?>): List<Farm?>
    suspend fun createFarm(farm: Farm): Unit

    suspend fun deleteFarm(farm: Farm): Unit

    suspend fun changeFarmName(newName: String, farm: Farm): Unit

    suspend fun farmExists(farmId: String): Boolean

    suspend fun farmDocId(farmId: String): String
}