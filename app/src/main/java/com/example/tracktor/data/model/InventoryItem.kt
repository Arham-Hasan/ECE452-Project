package com.example.tracktor.data.model

data class InventoryItem (
    val userStatList: MutableList<UserInventoryStat> = mutableListOf()
        )