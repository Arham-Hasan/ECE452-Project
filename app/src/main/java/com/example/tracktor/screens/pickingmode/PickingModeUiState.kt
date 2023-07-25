package com.example.tracktor.screens.pickingmode

data class PickingModeUiState(
    val dropDrownExtended: Boolean = false,
    val validItems: Set<String> = setOf(),
    val transactions: MutableMap<String, Int> = mutableMapOf<String, Int>()
)