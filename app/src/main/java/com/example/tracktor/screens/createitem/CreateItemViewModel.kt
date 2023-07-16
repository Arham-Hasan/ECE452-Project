package com.example.tracktor.screens.createitem

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_ITEM_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.InventoryRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CreateItemViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository,
                                              userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository)  {
    var uiState = mutableStateOf(CreateItemUiState())
        private set

    private val name
        get() = uiState.value.name

    fun onNameChange(newValue:String){
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onCreateItemClick(openAndPopUp: (String, String) -> Unit) {

        if(name.isBlank()){
            SnackbarManager.showMessage("Please create an item name".toSnackbarMessage())
            return
        }
        launchCatching{
            SnackbarManager.showMessage("Creating Item".toSnackbarMessage())
            delay(500)
            farmManagerRepository.addInventoryItem(itemName = uiState.value.name)
            openAndPopUp(INVENTORY_MODE_SCREEN, CREATE_ITEM_SCREEN)
        }
    }
}