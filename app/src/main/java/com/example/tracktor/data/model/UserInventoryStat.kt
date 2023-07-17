package com.example.tracktor.data.model

data class UserInventoryStat (
    val sellList : MutableList<UserTransaction> = mutableListOf<UserTransaction>(),
    val pickList : MutableList<UserTransaction> = mutableListOf<UserTransaction>(),
    val total : Int = 0
        )