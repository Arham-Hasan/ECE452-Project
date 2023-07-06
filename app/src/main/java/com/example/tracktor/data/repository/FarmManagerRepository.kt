package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmManagerRepository {

    fun changeSelectedFarm(farm:Farm): Unit

    fun removeSelectedFarm(): Unit
    fun getSelectedFarm(): Farm?

    suspend fun getFarms() : List<Farm?>
    suspend fun createFarm(name: String) : Unit

    suspend fun deleteFarm(farm: Farm): Unit

    suspend fun resquestToJoinFarm(farmId: String): Unit
}