package com.example.tracktor.data.model

data class UserInventoryStat (
    val sellList : MutableList<UserTransaction> = mutableListOf<UserTransaction>(),
    val pickList : MutableList<UserTransaction> = mutableListOf<UserTransaction>(),
    val pickTotal : Int = 0,
    val sellTotal : Int = 0
        )