package com.example.tracktor.screens.inventorymode

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.text.isDigitsOnly
import com.example.tracktor.CREATE_ITEM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.InventoryItem
import com.example.tracktor.data.model.UserTransaction
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

    fun onItemSave(price: String, quantity: String, uri:Uri, item: InventoryItem){
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


        val quantityDiff = quantity.toLong() - item.itemTotal
        val transaction = UserTransaction(amount = quantityDiff.toInt())
        launchCatching {
            farmManagerRepository.addPickTransaction(item.name, transaction)
            if(uri != Uri.EMPTY){
                farmManagerRepository.uploadInventoryItemImage(item.name, uri)
            }
            farmManagerRepository.updateInventoryItem(price.toDouble(), uri, item.name)

            retrieveItems()
        }

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
                val imageMap: MutableMap<InventoryItem, ImageBitmap> = mutableMapOf<InventoryItem, ImageBitmap>()
                items.forEach{item ->
                    val itemImage = farmManagerRepository.getInventoryItemImage(item)
                    val imageBitMap = byteArrayToBitmap(itemImage).asImageBitmap()
                    val inventoryItem = InventoryItem(
                        name = item,
                        itemTotal = quantityMap[item]!!,
                        itemPrice = priceMap[item]!!,
                    )
                    itemList.add(inventoryItem)
                    imageMap[inventoryItem] = imageBitMap
                }
                uiState.value = uiState.value.copy(items = itemList, imageMap = imageMap)
                Log.i("Inventory","Retrieved Items: "+uiState.value.items.toString())
            }



        }

    }
}