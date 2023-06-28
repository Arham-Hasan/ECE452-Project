package com.example.tracktor.screens.sellingmode

import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellingModeViewModel @Inject constructor() : TracktorViewModel() {
    fun onMicButtonClick(){
        SnackbarManager.showMessage("Listening".toSnackbarMessage())
    }

    fun onPickingClick(openScreen: (String) -> Unit){
        openScreen(PICKING_MODE_SCREEN)
    }
    fun onAnalyticsClick(openScreen: (String) -> Unit){
        openScreen(ANALYTICS_MODE_SCREEN)
    }
    fun onFridgesClick(openScreen: (String) -> Unit){
        openScreen(FRIDGE_MODE_SCREEN)
    }
    fun onInventoryClick(openScreen: (String) -> Unit){
        openScreen(INVENTORY_MODE_SCREEN)
    }

}