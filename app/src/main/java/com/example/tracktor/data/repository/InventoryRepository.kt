package com.example.tracktor.data.repository

interface InventoryRepository {
    suspend fun createInventory(id: String) : Unit
}