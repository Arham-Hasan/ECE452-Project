package com.example.tracktor.data.model

data class Inventory(
    val itemMap: MutableMap<String, InventoryItem> = mutableMapOf<String, InventoryItem>()
)