package com.example.tracktor.screens.createitem

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_ITEM_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CreateItemViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository) {

    private val currencyPattern = Regex("^\\d+(\\.\\d{1,2})?$")

    var uiState = mutableStateOf(CreateItemUiState())
        private set

    private val name
        get() = uiState.value.name

    private val price
        get() = uiState.value.price

    private val itemImage
        get() = uiState.value.itemImage

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue.trim())
    }

    fun onPriceChange(newValue: String) {
        if (newValue.startsWith("0")) {
            ""
        } else {
            uiState.value = uiState.value.copy(price = newValue.trim())
        }
    }

    fun onCreateItemClick(openAndPopUp: (String, String) -> Unit) {

        if (name.isBlank()) {
            SnackbarManager.showMessage("Please create an item name".toSnackbarMessage())
            return
        }

        if (price.isBlank()) {
            SnackbarManager.showMessage("Please add an item price".toSnackbarMessage())
            return
        }

        if (!currencyPattern.matches(price)) {
            SnackbarManager.showMessage("Please provide a valid item price".toSnackbarMessage())
            return
        }

        if (itemImage == Uri.EMPTY) {
            SnackbarManager.showMessage("Please provide a an image for the item".toSnackbarMessage())
            return
        }

        launchCatching {
            SnackbarManager.showMessage("Creating Item".toSnackbarMessage())
            delay(500)
            farmManagerRepository.addInventoryItem(itemName = name, price.toDouble(), itemImage)
            farmManagerRepository.uploadInventoryItemImage(name, itemImage)
            openAndPopUp(INVENTORY_MODE_SCREEN, CREATE_ITEM_SCREEN)
        }
    }

    fun handleImageUpload(uri: Uri) {
        uiState.value.itemImage = uri
    }

}