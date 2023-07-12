package com.example.tracktor.data.model

import java.util.Date

data class Transaction (
    val date: Date = Date(),
    val amount: Int = 0,
        )