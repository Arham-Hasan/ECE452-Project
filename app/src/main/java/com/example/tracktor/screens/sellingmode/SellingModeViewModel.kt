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
import com.example.tracktor.screens.TransactionTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SellingModeViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository,
                                               userManagerRepository: UserManagerRepository
) : TransactionTemplate(userManagerRepository) {


    var uiState = mutableStateOf(SellingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    private val validItems
        get() = uiState.value.validItems

    private val transactions
        get() = uiState.value.transactions

    private val itemToPriceMap
        get() = uiState.value.itemToPriceMap

    private val itemToQuantityMap
        get() = uiState.value.itemToQuantityMap

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun retrieveItemsAndPrices(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItemNames()?.toSet()
            if(itemSet != null) {
                uiState.value = uiState.value.copy(validItems = itemSet)
                Log.i("Selling", "Valid items: $validItems")
            }

            val priceMap = farmManagerRepository.getInventoryItemNamesToPrice()
            uiState.value = uiState.value.copy(itemToPriceMap = priceMap)
            Log.i("Selling", "Price Mapping: $itemToPriceMap")

            val quantityMap = farmManagerRepository.getInventoryItemNamesToQuantity()
            uiState.value = uiState.value.copy(itemToQuantityMap = quantityMap as MutableMap<String, Long>)
            Log.i("Selling", "Quantity Mapping: $itemToQuantityMap")
        }
    }

    fun saveTransactions() {
        if(transactions.isEmpty()){
            return
        }
        runBlocking{
            Log.i("Selling", "Recording items: $transactions")
            for (transaction in transactions) {
                Log.i("Selling","Recording ${transaction.key}, quantity: ${transaction.value}")
                val userTransaction = UserTransaction(date = Date(), amount = transaction.value)
                val price = itemToPriceMap.getOrDefault(transaction.key, 0.0)
                val sellTransaction = SellTransaction(userTransaction, price)
                farmManagerRepository.addSellTransaction(itemName = transaction.key, userTransaction = sellTransaction)
            }
        }
    }

    override fun verifyInput(input: String): Boolean {
        val inputArray = input.split(" ")

        if (inputArray.size > 2) {
            return false
        }

        val fruit = inputArray.last()
        val number = inputArray.first()
        if (fruit !in validItems){
            return false
        }

        if (!number.isDigitsOnly() && number !in numberMap){
            return false
        }

        if(convertNumberToInt(number) > itemToQuantityMap[fruit]!! ){
            return false
        }

        return true
    }

    override fun updateQuantity(quantity: Int, item: String) {
        itemToQuantityMap[item] = itemToQuantityMap[item]!! - quantity

        transactions[item] = transactions.getOrDefault(item, 0) + quantity
    }

    override fun displaySuccessMessage(quantity: Int, item: String) {
        SnackbarManager.showMessage("Sold $quantity $item".toSnackbarMessage())
    }


}