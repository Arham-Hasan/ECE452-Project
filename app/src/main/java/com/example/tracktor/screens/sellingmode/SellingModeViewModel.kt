package com.example.tracktor.screens.sellingmode

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.SellTransaction
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SellingModeViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository
                                               ,userManagerRepository: UserManagerRepository)
                                                : TracktorViewModel(userManagerRepository ) {

    var uiState = mutableStateOf(SellingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    private val itemPriceMap
        get() = uiState.value.itemToPriceMap

    private val itemQuantityMap
        get() = uiState.value.itemToQuantityMap

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    private val numberMap = mapOf(
        "zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    fun retrieveItemsAndPrices(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItemNames()?.toSet()
            if(itemSet == null) uiState.value = uiState.value.copy( validItems = setOf())
            else uiState.value = uiState.value.copy( validItems = itemSet)
            Log.i("Selling","Valid items: "+uiState.value.validItems.toString())

            val priceMap = farmManagerRepository.getInventoryItemNamesToPrice()
            uiState.value= uiState.value.copy(itemToPriceMap = priceMap)
            Log.i("Selling","Price Mapping: "+uiState.value.itemToPriceMap.toString())

            val quantityMap = farmManagerRepository.getInventoryItemNamesToQuantity()
            uiState.value= uiState.value.copy(itemToQuantityMap = quantityMap as MutableMap<String, Long>)
            Log.i("Selling","Quantity Mapping: "+uiState.value.itemToQuantityMap.toString())

        }
    }

    fun parseInput(speechInput: String){

        if (speechInput.isEmpty()){
            return
        }
        val speechLower = speechInput.lowercase()
        if (!verifyInput(speechLower)){
//            Not valid input return
            SnackbarManager.showMessage("Not a valid input, ignoring".toSnackbarMessage())
            return
        }

//        Record to db
        val inputArray = speechLower.split(" ")

        val item = inputArray.last()
        val quantity = convertNumberToInt(inputArray.first())

        if(quantity!!.toInt() > itemQuantityMap[item]!!){
            SnackbarManager.showMessage("Not enough ${item}(s) in inventory to sell".toSnackbarMessage())
            return
        }

        itemQuantityMap[item] = itemQuantityMap[item]!! - quantity.toInt()

        (uiState.value.transactions)[item] = (uiState.value.transactions).getOrDefault(item, 0) + quantity

        SnackbarManager.showMessage("Sold $quantity $item".toSnackbarMessage())
    }

    private fun verifyInput(input: String): Boolean {
//        For inputs we expect the format "Number Fruit"
        val inputArray = input.split(" ")

        if (inputArray.size > 2) {
            return false
        }

        val fruit = inputArray.last()
        val number = inputArray.first()
        if (fruit !in uiState.value.validItems){
            return false
        }

        if (!number.isDigitsOnly() && number !in numberMap){
            return false
        }
        return true
    }


    private fun convertNumberToInt(number: String): Int? {

        if (number in numberMap){
            return numberMap[number]
        }

        return number.toInt()
    }

    fun saveTransactions() {
        if(uiState.value.transactions.isEmpty()){
            return
        }
        runBlocking{
            Log.i("Selling","Recording items: "+uiState.value.transactions.toString())
            for (transaction in uiState.value.transactions) {
                Log.i("Selling","Recording ${transaction.key}, quantity: ${transaction.value}")
                val userTransaction = UserTransaction(date = Date(), amount = transaction.value)
                val price = itemPriceMap.getOrDefault(transaction.key, 0.0)
                val sellTransaction = SellTransaction(userTransaction, price)
                farmManagerRepository.addSellTransaction(itemName = transaction.key, userTransaction = sellTransaction)
            }
        }
    }


}