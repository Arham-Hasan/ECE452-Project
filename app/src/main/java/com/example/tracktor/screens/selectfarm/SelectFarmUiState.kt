package com.example.tracktor.screens.selectfarm

import com.example.tracktor.data.model.Farm

data class SelectFarmUiState(
    val farms: List<Farm?> = listOf<Farm>(),
    val dropDrownExtended: Boolean = false
)
