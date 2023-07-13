package com.example.tracktor.screens.inventorymode

import com.example.tracktor.data.model.InventoryItem

data class InventoryModeUiState (
    val dropDrownExtended: Boolean = false,
    val items : List<InventoryItem> = listOf<InventoryItem>(),
)
