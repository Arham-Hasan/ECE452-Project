package com.example.tracktor.abstractmodel

import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage

class PickTransactionViewModel() : Transaction() {

    override fun compileTransaction(input: String) {
        val inputArray = input.split(" ")

        val item = inputArray.last()
        val quantity = convertNumberToInt(inputArray.first())

        transactions[item] = transactions.getOrDefault(item, 0) + quantity!!
        SnackbarManager.showMessage("Picked ${transactions[item]} $item".toSnackbarMessage())
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



}
