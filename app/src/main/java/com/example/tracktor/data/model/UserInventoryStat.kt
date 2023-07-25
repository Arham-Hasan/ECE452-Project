package com.example.tracktor.data.model

data class UserInventoryStat (
    val sellList : MutableList<SellTransaction> = mutableListOf<SellTransaction>(),
    val pickList : MutableList<UserTransaction> = mutableListOf<UserTransaction>(),
    val pickTotal : Int = 0,
    val sellTotal : Int = 0,
    val revenueTotal: Double = 0.0
        )