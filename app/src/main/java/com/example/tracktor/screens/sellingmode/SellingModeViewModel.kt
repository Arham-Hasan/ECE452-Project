package com.example.tracktor.screens.sellingmode

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.abstractmodel.SellTransactionViewModel
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
class SellingModeViewModel @Inject constructor(private val farmManagerRepository: FarmManagerRepository,
                                               userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository ) {

    val sellTransaction: SellTransactionViewModel = SellTransactionViewModel()

    var uiState = mutableStateOf(SellingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun retrieveItemsAndPrices(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItemNames()?.toSet()
            if(itemSet != null) {
                sellTransaction.validItems = itemSet
                Log.i("Selling","Valid items: "+sellTransaction.validItems.toString())
            }

            val priceMap = farmManagerRepository.getInventoryItemNamesToPrice()
            sellTransaction.itemToPriceMap = priceMap
            Log.i("Selling","Price Mapping: "+sellTransaction.itemToPriceMap.toString())

            val quantityMap = farmManagerRepository.getInventoryItemNamesToQuantity()
            sellTransaction.itemToQuantityMap = quantityMap as MutableMap<String, Long>
            Log.i("Selling","Quantity Mapping: "+sellTransaction.itemToQuantityMap.toString())
        }
    }:

    fun saveTransactions() {
        if(sellTransaction.transactions.isEmpty()){
            return
        }
        runBlocking{
            Log.i("Selling","Recording items: "+sellTransaction.transactions.toString())
            for (transaction in sellTransaction.transactions) {
                Log.i("Selling","Recording ${transaction.key}, quantity: ${transaction.value}")
                val userTransaction = UserTransaction(date = Date(), amount = transaction.value)
                val price = sellTransaction.itemToPriceMap.getOrDefault(transaction.key, 0.0)
                val sellTransaction = SellTransaction(userTransaction, price)
                farmManagerRepository.addSellTransaction(itemName = transaction.key, userTransaction = sellTransaction)
            }
        }
    }


}