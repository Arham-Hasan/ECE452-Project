package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmUserRepository {
    suspend fun createFarm(farm: Farm, userId: String) : Unit

    suspend fun getFarmIds(userId: String): List<String?>
}