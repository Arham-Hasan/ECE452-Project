package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmUserRepository {
    suspend fun createFarm(farm: Farm, userId: String) : Unit

    suspend fun getActiveFarmIds(userId: String): List<String?>

    suspend fun isAdmin(userId: String, farm: Farm): Boolean

    suspend fun deleteFarm(farm:Farm) : Unit

    suspend fun isActiveFarmMember(userId:String, farmId: String): Boolean

    suspend fun isNonActiveFarmMember(userId:String, farmId: String): Boolean

    suspend fun isActiveOrNonActiveFarmMember(userId:String, farmId: String): Boolean

    suspend fun requestToJoinFarm(userId:String, farmId: String): Unit
}