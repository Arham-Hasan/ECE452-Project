package com.example.tracktor.screens.inventorymode

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryModeViewModel @Inject constructor(authRepository: AuthRepository) : TracktorViewModel(authRepository) {

    var uiState = mutableStateOf(InventoryModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

}