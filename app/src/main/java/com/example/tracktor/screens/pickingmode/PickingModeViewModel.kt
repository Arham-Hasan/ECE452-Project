package com.example.tracktor.screens.pickingmode

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PickingModeViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository)  {

    var uiState = mutableStateOf(PickingModeUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    private val numberMap = mapOf(
        "zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    fun retrieveItems(){
        launchCatching {
            val itemSet = farmManagerRepository.getInventoryItems()?.toSet()
            if(itemSet == null) uiState.value = uiState.value.copy( validItems = setOf())
            else uiState.value = uiState.value.copy( validItems = itemSet)
            Log.i("Picking","Valid items: "+uiState.value.validItems.toString())
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


        val pickRecord = Pair(convertNumberToInt(inputArray.first()), inputArray.last())

        SnackbarManager.showMessage("Picked ${pickRecord.first} ${pickRecord.second}".toSnackbarMessage())
        val pickTransaction = UserTransaction(date = Date(), amount = pickRecord.first!!)
        runBlocking{
            farmManagerRepository.addPickTransaction(itemName = pickRecord.second, userTransaction = pickTransaction)
        }

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



}