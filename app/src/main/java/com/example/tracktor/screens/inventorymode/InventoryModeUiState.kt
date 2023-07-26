package com.example.tracktor.screens.inventorymode

import androidx.compose.ui.graphics.ImageBitmap
import com.example.tracktor.data.model.InventoryItem

data class InventoryModeUiState (
    val dropDrownExtended: Boolean = false,
    val items : List<InventoryItem> = listOf<InventoryItem>(),
    val imageMap: Map<InventoryItem, ImageBitmap> = mutableMapOf<InventoryItem, ImageBitmap>()
)
