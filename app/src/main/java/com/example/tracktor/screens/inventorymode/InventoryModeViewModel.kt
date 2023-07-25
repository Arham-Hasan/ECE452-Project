package com.example.tracktor.screens.inventorymode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.text.isDigitsOnly
import com.example.tracktor.CREATE_ITEM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.InventoryItem
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

    private val currencyPattern = Regex("^\\d+(\\.\\d{1,2})?$")


    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun addItemToInventory(openScreen: (String) -> Unit){
        openScreen(CREATE_ITEM_SCREEN)
    }

    fun onItemSave(price: String, quantity: String, item: String){
        SnackbarManager.showMessage("newPrice: $price, newQuantity: $quantity".toSnackbarMessage())
        if(price.isBlank()){
            SnackbarManager.showMessage("Please add a price".toSnackbarMessage())
            return
        }

        if(quantity.isBlank()){
            SnackbarManager.showMessage("Please add a quantity".toSnackbarMessage())
            return
        }

        if(!currencyPattern.matches(price)){
            SnackbarManager.showMessage("Please provide a valid price".toSnackbarMessage())
            return
        }

        if(!quantity.isDigitsOnly()){
            SnackbarManager.showMessage("Please provide a valid quantity".toSnackbarMessage())
            return
        }


        // logic for updating the price and quantity of the item
//        launchCatching {
//            farmManagerRepository.updateInventoryItem(price.toDouble(), quantity.toLong(), item)
//        }
        retrieveItems()
    }


    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    fun retrieveItems(){
        launchCatching {
            val priceMap = farmManagerRepository.getInventoryItemNamesToPrice()

            val quantityMap = farmManagerRepository.getInventoryItemNamesToQuantity()

            val items = farmManagerRepository.getInventoryItemNames()
            if(items != null){
                val itemList: MutableList<InventoryItem> = mutableListOf<InventoryItem>()

                items.forEach{item ->
                    val itemImage = farmManagerRepository.getInventoryItemImage(item)
                    val imageBitMap = byteArrayToBitmap(itemImage).asImageBitmap()
                    val inventoryItem = InventoryItem(
                        name = item,
                        itemTotal = quantityMap[item]!!,
                        itemPrice = priceMap[item]!!,
                        imageBitmap = imageBitMap
                    )
                    itemList.add(inventoryItem)
                }
                uiState.value = uiState.value.copy(items = itemList)
            }



        }

    }
}