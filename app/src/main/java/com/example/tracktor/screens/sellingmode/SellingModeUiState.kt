package com.example.tracktor.screens.sellingmode

data class SellingModeUiState (
    val dropDrownExtended: Boolean = false,
    val validItems: Set<String> = setOf(),
    val transactions: MutableMap<String, Int> = mutableMapOf<String, Int>()
)