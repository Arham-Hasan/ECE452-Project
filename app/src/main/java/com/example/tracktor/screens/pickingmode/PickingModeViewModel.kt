package com.example.tracktor.screens.pickingmode

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.abstractmodel.PickTransactionViewModel
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PickingModeViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository,
) : TracktorViewModel(userManagerRepository)
{

    val pickTransactionViewModel: PickTransactionViewModel = PickTransactionViewModel()

    var uiState = mutableStateOf(PickingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    fun retrieveItems(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItemNames()
            if(itemSet != null){
                pickTransactionViewModel.validItems = (itemSet.map { item -> item.lowercase() }.toSet())
                Log.i("Picking","Valid items: "+pickTransactionViewModel.validItems.toString())
            }
        }
    }

    fun saveTransactions() {
        if(pickTransactionViewModel.transactions.isEmpty()){
            return
        }
        runBlocking{
            Log.i("Picking","Recording items: "+pickTransactionViewModel.transactions.toString())

            for (transaction in pickTransactionViewModel.transactions) {
                Log.i("Picking","Recording ${transaction.key}, quantity: ${transaction.value}")
                val pickTransaction = UserTransaction(date = Date(), amount = transaction.value)
                farmManagerRepository.addPickTransaction(itemName = transaction.key, userTransaction = pickTransaction)
            }
        }
    }

}