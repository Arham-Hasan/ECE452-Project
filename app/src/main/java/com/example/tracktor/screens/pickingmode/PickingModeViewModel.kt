package com.example.tracktor.screens.pickingmode

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TransactionTemplate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PickingModeViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository,
) : TransactionTemplate(userManagerRepository)
{

    var uiState = mutableStateOf(PickingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    private val validItems
        get() = uiState.value.validItems

    private val transactions
        get() = uiState.value.transactions
    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun retrieveItems(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItemNames()?.toSet()
            if(itemSet == null) uiState.value = uiState.value.copy( validItems = setOf())
            else uiState.value = uiState.value.copy( validItems = itemSet)
            Log.i("Picking", "Valid items: $itemSet")
        }
    }

    fun saveTransactions() {
        if(transactions.isEmpty()){
            return
        }
        runBlocking{
            Log.i("Picking", "Recording items: $transactions")

            for (transaction in transactions) {
                Log.i("Picking","Recording ${transaction.key}, quantity: ${transaction.value}")
                val pickTransaction = UserTransaction(date = Date(), amount = transaction.value)
                farmManagerRepository.addPickTransaction(itemName = transaction.key, userTransaction = pickTransaction)
            }
        }
    }

    override fun verifyInput(input: String): Boolean {
        val speechLower = input.lowercase()

        val inputArray = speechLower.split(" ")

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
        return true
    }

    override fun updateQuantity(quantity: Int, item: String) {
        transactions[item] = transactions.getOrDefault(item, 0) + quantity!!
    }

    override fun displaySuccessMessage(quantity: Int, item: String) {
        SnackbarManager.showMessage("Picked ${transactions[item]} $item".toSnackbarMessage())
    }


}