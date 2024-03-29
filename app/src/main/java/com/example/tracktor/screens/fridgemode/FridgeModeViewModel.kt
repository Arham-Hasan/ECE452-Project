package com.example.tracktor.screens.fridgemode

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.FRIDGE_NAME
import com.example.tracktor.MARKET_SCREEN
import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.common.Fridges.getFridges
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

    private val fridges
        get() = uiState.value.fridges

    private val mapAlertMap
        get() = uiState.value.mapAlertMap

    private val mapAlert
        get() = uiState.value.mapAlert
    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }
    init {
        uiState.value = uiState.value.copy(fridges = getFridges())
        val temp = mutableMapOf<String,Boolean>()
        fridges.forEach { fridge ->
            temp[fridge.name] = false
        }
        uiState.value = uiState.value.copy(mapAlertMap=temp)
    }

    fun onMarkerClick(openScreen: (String) -> Unit, fridgename: String,){
        println("$MARKET_SCREEN?$FRIDGE_NAME=${fridgename}")
        openScreen("$MARKET_SCREEN?$FRIDGE_NAME=${fridgename}")

    }
    fun toggleAlert(fridge: Fridge){
        val temp = mapAlertMap.toMutableMap()
        temp[fridge.name] = !temp[fridge.name]!!
        uiState.value = uiState.value.copy(mapAlertMap = temp)
    }
}