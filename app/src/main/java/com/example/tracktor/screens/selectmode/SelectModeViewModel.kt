package com.example.tracktor.screens.selectmode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.screens.TracktorViewModel
import com.example.tracktor.screens.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectModeViewModel @Inject constructor(accountService:AccountService) : TracktorViewModel(accountService){
    var uiState = mutableStateOf(SelectModeUiState())
        private set
    fun setStateFarmId(newValue:String){
        if(newValue != ""){
            uiState.value = uiState.value.copy(farmId = newValue)
        }
    }

    fun getFarmId() : String {
        return uiState.value.farmId
    }
    fun onPickingModeClick(navigate: (String) -> Unit){
        navigate(PICKING_MODE_SCREEN)
    }

}