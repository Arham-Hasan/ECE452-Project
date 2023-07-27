package com.example.tracktor.abstractmodel

import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import java.util.Date

sealed class Transaction(val date: Date = Date(), val amount: Int = 0) {

    var validItems: Set<String> = setOf()
    val transactions: MutableMap<String, Int> = mutableMapOf<String, Int>()


    val numberMap = mapOf(
        "zero" to 0, "one" to 1, "two" to 2, "three" to 3, "four" to 4,
        "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )

    fun convertNumberToInt(number: String): Int {

        if (number in numberMap){
            return numberMap[number]!!
        }

        return number.toInt()
    }

    // Template method that defines the common transaction process
    fun processTransaction(speechInput: String) {
        if (speechInput.isEmpty()){
            return
        }
        if (!verifyInput(speechInput)) {
            SnackbarManager.showMessage("Not a valid input, ignoring".toSnackbarMessage())
            return
        }

        compileTransaction(speechInput)
    }

    abstract fun verifyInput(input: String): Boolean

    abstract fun compileTransaction(input: String)
}
