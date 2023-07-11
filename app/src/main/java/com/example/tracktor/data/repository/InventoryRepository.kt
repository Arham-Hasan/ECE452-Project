package com.example.tracktor.data.repository

interface InventoryRepository {
    suspend fun itemExists(name: String, farmId: String) : Boolean
    suspend fun addItem(name: String, farmId: String) : Unit
}