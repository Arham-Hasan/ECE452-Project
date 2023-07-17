package com.example.tracktor.screens.pickingmode

data class PickingModeUiState (
    val dropDrownExtended: Boolean = false,
    val validItems: Set<String> = setOf()
)