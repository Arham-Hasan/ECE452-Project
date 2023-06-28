package com.example.tracktor.screens.pickingmode

import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.SELLING_MODE_SCREEN
import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickingModeViewModel @Inject constructor(
) : TracktorViewModel()  {
    private val validFruits: Set<String> = setOf(
        "apple", "banana", "orange","apples", "bananas", "oranges"
    )
    private val numberMap = mapOf(
        "zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

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
    }

    private fun verifyInput(input: String): Boolean {
//        For inputs we expect the format "Number Fruit"
        val inputArray = input.split(" ")

        if (inputArray.size > 2) {
            return false
        }

        val fruit = inputArray.last()
        val number = inputArray.first()
        if (fruit !in validFruits){
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

    fun onSellingClick(openScreen: (String) -> Unit){
        openScreen(SELLING_MODE_SCREEN)
    }
    fun onAnalyticsClick(openScreen: (String) -> Unit){
        openScreen(ANALYTICS_MODE_SCREEN)
    }
    fun onFridgesClick(openScreen: (String) -> Unit){
        openScreen(FRIDGE_MODE_SCREEN)
    }
    fun onInventoryClick(openScreen: (String) -> Unit){
        openScreen(INVENTORY_MODE_SCREEN)

    }
}