package com.example.tracktor.screens.joinfarm

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_FARM_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class JoinFarmViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository,
                                              userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository)  {
    var uiState = mutableStateOf(JoinFarmUiState())
        private set

    private val name
        get() = uiState.value.farmId

    fun onNameChange(newValue:String){
        uiState.value = uiState.value.copy(farmId = newValue)
    }

    fun onJoinFarmClick(openAndPopUp: (String, String) -> Unit) {
        if(name.isBlank()){
            SnackbarManager.showMessage("Please enter a farm ID".toSnackbarMessage())
            return
        }
        launchCatching{
            SnackbarManager.showMessage("Sending request to join farm".toSnackbarMessage())
            delay(500)
            farmManagerRepository.requestToJoinFarm(uiState.value.farmId)
            openAndPopUp(SELECT_FARM_SCREEN, CREATE_FARM_SCREEN)
        }
    }
}