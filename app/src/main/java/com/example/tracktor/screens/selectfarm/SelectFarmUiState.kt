package com.example.tracktor.screens.selectfarm

import com.example.tracktor.model.Farm

data class SelectFarmUiState (
    val farms : List<Farm?> = listOf<Farm>()
)
