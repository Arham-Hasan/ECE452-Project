package com.example.tracktor.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class TracktorViewModel : ViewModel(){
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, _ ->

            },
            block = block
        )


    fun onSellingClick(openScreen: (String) -> Unit){
        openScreen(SELLING_MODE_SCREEN)
    }
    fun onAnalyticsClick(openScreen: (String) -> Unit){
        openScreen(ANALYTICS_MODE_SCREEN)
    }
    fun onPickingClick(openScreen: (String) -> Unit){
        openScreen(PICKING_MODE_SCREEN)
    }
    fun onInventoryClick(openScreen: (String) -> Unit){
        openScreen(INVENTORY_MODE_SCREEN)
    }
    fun onFridgesClick(openScreen: (String) -> Unit){
        openScreen(FRIDGE_MODE_SCREEN)
    }

    fun onSignOutClick(){
        SnackbarManager.showMessage("Sign out".toSnackbarMessage())
    }

    fun onSettingsClick(){
        SnackbarManager.showMessage("Settings".toSnackbarMessage())
    }

    fun onChangeFarmClick(openScreen: (String)->Unit){
        openScreen(SELECT_FARM_SCREEN)

    }
}