package com.example.tracktor.screens

import com.example.tracktor.data.repository.UserManagerRepository

abstract class TransactionTemplate(
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository){

    fun processTransaction(speechInput: String) {
        if (!verifyInput(speechInput)){
            return
        }
        compileTransaction(speechInput)
    }

    abstract fun verifyInput(input: String):Boolean

    fun compileTransaction(input: String) {
        val inputArray = input.split(" ")

        val item = inputArray.last()
        val quantity = convertNumberToInt(inputArray.first())

        updateQuantity(quantity,item)

        displaySuccessMessage(quantity, item)
    }

    abstract fun updateQuantity(quantity:Int, item:String)


    abstract fun displaySuccessMessage(quantity:Int, item:String)


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
}