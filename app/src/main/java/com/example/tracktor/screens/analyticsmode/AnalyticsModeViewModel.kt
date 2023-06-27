package com.example.tracktor.screens.analyticsmode

import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalyticsModeViewModel @Inject constructor() : TracktorViewModel() {

    fun onSellingClick(openScreen: (String) -> Unit){
        openScreen(SELLING_MODE_SCREEN)
    }
    fun onPickingClick(openScreen: (String) -> Unit){
        openScreen(PICKING_MODE_SCREEN)
    }
    fun onFridgesClick(openScreen: (String) -> Unit){
        openScreen(FRIDGE_MODE_SCREEN)
    }
    fun onInventoryClick(openScreen: (String) -> Unit){
        openScreen(INVENTORY_MODE_SCREEN)
    }

}