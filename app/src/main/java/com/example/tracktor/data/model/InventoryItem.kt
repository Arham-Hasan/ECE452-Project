package com.example.tracktor.data.model

data class InventoryItem (
    val userStatMap: MutableMap<String,UserInventoryStat> = mutableMapOf<String,UserInventoryStat>(),
    val name: String = ""
)