package com.example.tracktor.data.model

data class UserInventoryStat (
    val sellList : MutableList<Transaction> = mutableListOf<Transaction>(),
    val picklist : MutableList<Transaction> = mutableListOf<Transaction>(),
    val total : Int = 0
        )