package com.example.tracktor.data.model

import androidx.compose.ui.graphics.ImageBitmap

data class InventoryItem (
    val userStats: MutableMap<String,UserInventoryStat> = mutableMapOf<String,UserInventoryStat>(),
    val name: String = "",
    val itemTotal : Long = 0,
    val itemPrice: Double = 0.0,
    val imageRef: String? = null,
)