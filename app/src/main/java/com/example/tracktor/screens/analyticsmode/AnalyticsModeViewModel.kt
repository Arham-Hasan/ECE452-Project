package com.example.tracktor.screens.analyticsmode

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsModeViewModel @Inject constructor(authRepository: AuthRepository) : TracktorViewModel(authRepository) {
    var uiState = mutableStateOf(AnalyticsModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

}