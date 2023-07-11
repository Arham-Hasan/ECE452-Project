package com.example.tracktor.data.model

data class InventoryItem (
    val userStatList: MutableMap<String,UserInventoryStat> = mutableMapOf<String,UserInventoryStat>()
        )