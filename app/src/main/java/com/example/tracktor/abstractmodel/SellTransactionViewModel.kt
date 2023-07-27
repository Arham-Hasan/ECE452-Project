package com.example.tracktor.abstractmodel

import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage


class SellTransactionViewModel() : Transaction() {

    var itemToPriceMap: Map<String,Double> = mapOf<String,Double>()
    var itemToQuantityMap: MutableMap<String,Long> = mutableMapOf<String,Long>()

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

    override fun compileTransaction(input: String) {
        val inputArray = input.split(" ")

        val item = inputArray.last()
        val quantity = convertNumberToInt(inputArray.first())

        itemToQuantityMap[item] = itemToQuantityMap[item]!! - quantity

        transactions[item] = transactions.getOrDefault(item, 0) + quantity

        SnackbarManager.showMessage("Sold $quantity $item".toSnackbarMessage())
    }

}
