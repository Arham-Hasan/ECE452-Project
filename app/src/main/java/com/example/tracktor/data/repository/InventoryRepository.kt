package com.example.tracktor.data.repository

interface InventoryRepository {
    suspend fun addItem(name: String, farmId: String) : Unit
}