package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmUserRepository {
    suspend fun createFarm(farm: Farm, userId: String) : Unit

    suspend fun getFarmIds(userId: String): List<String?>

    suspend fun isAdmin(userId: String, farm: Farm): Boolean

    suspend fun deleteFarm(farm:Farm) : Unit

    suspend fun isFarmMember(userId:String, farmId: String): Boolean
}