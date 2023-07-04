package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmRepository {
    suspend fun getFarms(farmIds: List<String?>) : List<Farm?>
    suspend fun createFarm(farm:Farm) : Unit
}