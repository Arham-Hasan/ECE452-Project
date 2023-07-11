package com.example.tracktor.screens.fridgemode

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FridgeModeViewModel @Inject constructor(userManagerRepository: UserManagerRepository) : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(FridgeModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

}