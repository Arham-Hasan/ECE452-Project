package com.example.tracktor.screens.farmsettings

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.MANAGE_MEMBERS_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FarmSettingsViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
)
    : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(FarmSettingsUiState())
        private set

    private val deleteFarmAlert
        get() = uiState.value.deleteFarmAlert

    private val changeFarmNameAlert
        get() = uiState.value.changeFarmNameAlert

    private val newFarmName
        get() = uiState.value.newFarmName

    init {
        runBlocking {
            uiState.value = uiState.value.copy(isAdmin = farmManagerRepository.isAdmin())
        }
    }

    fun onNewFarmNameChange(newValue:String){
        uiState.value = uiState.value.copy(newFarmName = newValue)
    }

    fun toggleDeleteFarmAlert(){
        uiState.value = uiState.value.copy(deleteFarmAlert = !deleteFarmAlert)
    }
    fun toggleChangeFarmNameAlert(){
        uiState.value = uiState.value.copy(changeFarmNameAlert = !changeFarmNameAlert)
    }

    fun onDeleteFarmClick(){
        toggleDeleteFarmAlert()
    }

    fun comfirmDeleteFarm(clearAndNavigate:(String)->Unit){
        toggleDeleteFarmAlert()
        launchCatching{
            farmManagerRepository.deleteFarm()
            clearAndNavigate(SELECT_FARM_SCREEN)
            SnackbarManager.showMessage("Farm Successfully Deleted".toSnackbarMessage())
        }
    }

    fun comfirmRenameFarm(clearAndNavigate:(String)->Unit){
        if(newFarmName.isEmpty()){
            SnackbarManager.showMessage("Please enter a farm name".toSnackbarMessage())
        }
        launchCatching{
            farmManagerRepository.changeFarmName(newFarmName)
            clearAndNavigate(SELECT_FARM_SCREEN)
            SnackbarManager.showMessage("Sucessfully renamed farm to $newFarmName".toSnackbarMessage())
        }
    }

    fun onManageMembersClick(openScreen: (String) -> Unit){
        openScreen(MANAGE_MEMBERS_SCREEN)
    }

    fun onChangeFarmNameClick(){
        onNewFarmNameChange("")
        toggleChangeFarmNameAlert()
    }

    fun onLeaveFarmClick(){

    }
}