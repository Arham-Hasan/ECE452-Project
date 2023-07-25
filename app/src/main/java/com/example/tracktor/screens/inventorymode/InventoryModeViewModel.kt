package com.example.tracktor.screens.inventorymode

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_ITEM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class InventoryModeViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository,
                                                 userManagerRepository: UserManagerRepository) : TracktorViewModel(userManagerRepository) {

    var uiState = mutableStateOf(InventoryModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun addItemToInventory(openScreen: (String) -> Unit){
        openScreen(CREATE_ITEM_SCREEN)
    }

    fun onSelectItemClick(openScreen: (String) -> Unit, item: String) {
//        go to manage item page
        SnackbarManager.showMessage("Going to manage item $item Page".toSnackbarMessage())

    }

    fun retrieveItems(){
        launchCatching {
            uiState.value = uiState.value.copy(items = farmManagerRepository.getInventoryItemNames())
        }

    }
}