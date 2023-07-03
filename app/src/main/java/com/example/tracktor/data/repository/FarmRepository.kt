package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmRepository {
    suspend fun getFarms(userId: String) : List<Farm?>
    suspend fun createFarm(name: String, userId: String) : Unit
}