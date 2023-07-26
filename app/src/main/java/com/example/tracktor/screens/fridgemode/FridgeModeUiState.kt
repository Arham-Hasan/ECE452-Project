package com.example.tracktor.screens.fridgemode

import com.example.tracktor.common.Fridges.Fridge

data class FridgeModeUiState(
    val dropDrownExtended: Boolean = false,
    val fridges: List<Fridge> = listOf(),
    val mapAlert: Boolean = false,
    val mapAlertMap: MutableMap<String, Boolean> = mutableMapOf()
)