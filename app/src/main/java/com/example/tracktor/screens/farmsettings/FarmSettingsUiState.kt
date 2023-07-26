package com.example.tracktor.screens.farmsettings

data class FarmSettingsUiState(
    val deleteFarmAlert: Boolean = false,
    val changeFarmNameAlert: Boolean = false,
    val isAdmin: Boolean = false,
    val newFarmName: String = ""
)