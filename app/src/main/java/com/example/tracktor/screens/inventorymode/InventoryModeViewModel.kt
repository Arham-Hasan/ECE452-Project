package com.example.tracktor.screens.inventorymode

import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryModeViewModel @Inject constructor() : TracktorViewModel() {

    fun onSellingClick(openScreen: (String) -> Unit){
        openScreen(SELLING_MODE_SCREEN)
    }
    fun onAnalyticsClick(openScreen: (String) -> Unit){
        openScreen(ANALYTICS_MODE_SCREEN)
    }
    fun onFridgesClick(openScreen: (String) -> Unit){
        openScreen(FRIDGE_MODE_SCREEN)
    }
    fun onPickingClick(openScreen: (String) -> Unit){
        openScreen(PICKING_MODE_SCREEN)
    }

}