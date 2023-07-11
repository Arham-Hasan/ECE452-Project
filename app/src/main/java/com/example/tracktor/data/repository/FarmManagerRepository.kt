package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm

interface FarmManagerRepository {

    fun changeSelectedFarm(farm:Farm): Unit

    fun removeSelectedFarm(): Unit
    fun getSelectedFarm(): Farm?

    suspend fun getActiveFarms() : List<Farm?>
    suspend fun createFarm(name: String) : Unit

    suspend fun deleteFarm(): Unit

    suspend fun requestToJoinFarm(farmId: String): Unit

    suspend fun isAdmin():Boolean
}