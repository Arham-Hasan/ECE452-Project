package com.example.tracktor.screens.createfarm

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_FARM_SCREEN
import com.example.tracktor.LOGIN_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.common.functions.isValidEmail
import com.example.tracktor.common.functions.isValidPassword
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.screens.TracktorViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

class CreateFarmViewModel @Inject constructor() : TracktorViewModel()  {
    var uiState = mutableStateOf(CreateFarmUiState())
        private set

    private val name
        get() = uiState.value.name

    fun onNameChange(newValue:String){
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onCreateFarmClick(openAndPopUp: (String, String) -> Unit) {

        if(name.isBlank()){
            SnackbarManager.showMessage("Please create a farm name".toSnackbarMessage())

            return
        }
        launchCatching{
            SnackbarManager.showMessage("Creating Farm".toSnackbarMessage())
            delay(500)
            openAndPopUp(SELECT_FARM_SCREEN, CREATE_FARM_SCREEN)
        }
    }
}