package com.example.tracktor.screens.selectfarm
import com.example.tracktor.CREATE_FARM_SCREEN
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.FARM_ID
import com.example.tracktor.SELECT_MODE_SCREEN
import com.example.tracktor.model.Farm
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.model.service.FarmStorageService
import com.example.tracktor.model.service.implementation.FarmStorageServiceImplementation
import com.example.tracktor.screens.TracktorViewModel
import com.example.tracktor.screens.selectmode.SelectModeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SelectFarmViewModel @Inject constructor(
    private val farmStorageService: FarmStorageService,
    private val accountService: AccountService)
    : TracktorViewModel(){

    var uiState = mutableStateOf(SelectFarmUiState())
        private set
    init{
        runBlocking{
            uiState.value = uiState.value.copy(farms = farmStorageService.getFarmsFromUserId(accountService.currentUserId))
        }
    }

    private fun retrieveFarms(){
        // Simulated data retrieval
        // return listOf(Farm(id="1", name = "Heeko farm"), Farm(id="2", name = "Boge farm"), Farm(id="3", name = "Arham farm"))
        runBlocking{
            uiState.value = uiState.value.copy(farms = farmStorageService.getFarmsFromUserId(accountService.currentUserId))
        }
    }

    fun onFarmNameClick(openScreen: (String) -> Unit, farm_id: String,){
        openScreen("$SELECT_MODE_SCREEN?$FARM_ID=${farm_id}")
    }

    fun onCreateFarmClick(openScreen: (String)->Unit){
        openScreen(CREATE_FARM_SCREEN)
    }
}