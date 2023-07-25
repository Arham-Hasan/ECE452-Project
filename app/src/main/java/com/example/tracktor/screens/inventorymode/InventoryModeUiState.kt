package com.example.tracktor.screens.inventorymode

data class InventoryModeUiState (
    val dropDrownExtended: Boolean = false,
    val items : List<String>? = listOf<String>(),
)
