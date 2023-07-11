package com.example.tracktor.data.model

data class Inventory (
    val id : String = "",
    val itemList: MutableList<InventoryItem> = mutableListOf<InventoryItem>()
        )