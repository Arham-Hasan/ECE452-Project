package com.example.tracktor.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FARM_SETTINGS_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.LOGIN_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.UserManagerRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class TracktorViewModel(
    protected val userManagerRepository: UserManagerRepository,
) : ViewModel(){
    fun launchCatching(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                SnackbarManager.showMessage(throwable.toSnackbarMessage())
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

    fun bottomNavBarActions(openScreen: (String) -> Unit): List<()->Unit> {
        return listOf(
            { onPickingClick(openScreen) },
            { onSellingClick(openScreen) },
            { onFridgesClick(openScreen) },
            { onAnalyticsClick(openScreen) },
            { onInventoryClick(openScreen) }
        )
    }
    fun onSignOutClick(clearAndNavigate: (String)->Unit){
        SnackbarManager.showMessage("Sign out".toSnackbarMessage())
        launchCatching{
            userManagerRepository.signOut()
            clearAndNavigate(LOGIN_SCREEN)
        }
    }

//    fun onUserSettingsClick(){
//        SnackbarManager.showMessage("User Settings".toSnackbarMessage())
//    }

    fun onFarmSettingsClick(openScreen: (String) -> Unit){
        openScreen(FARM_SETTINGS_SCREEN)
    }

    fun onChangeFarmClick(clearAndNavigate: (String)->Unit){
        clearAndNavigate(SELECT_FARM_SCREEN)
    }

    fun dropDownActionsAfterFarmSelected(openScreen: (String) -> Unit, clearAndNavigate:(String)->Unit): List<Pair<String,()->Unit>>{
        return listOf(
            Pair("Sign Out", {onSignOutClick(clearAndNavigate)}),
//            Pair("User Settings", {onUserSettingsClick()}),
            Pair("Farm Settings", {onFarmSettingsClick(openScreen)}),
            Pair("Change Farm",{onChangeFarmClick(clearAndNavigate)})
        )
    }

    fun dropDownActionsBeforeFarmSelected(openScreen: (String) -> Unit,clearAndNavigate:(String)->Unit): List<Pair<String,()->Unit>>{
        return listOf(
            Pair("Sign Out", {onSignOutClick(clearAndNavigate)}),
//            Pair("User Settings", {onUserSettingsClick()}),
        )
    }


}