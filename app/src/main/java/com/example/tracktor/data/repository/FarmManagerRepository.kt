package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmManagerRepository {
    suspend fun getFarms() : List<Farm?>
    suspend fun createFarm(name: String) : Unit
}