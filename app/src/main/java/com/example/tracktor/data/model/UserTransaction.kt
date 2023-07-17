package com.example.tracktor.data.model

import java.util.Date

data class UserTransaction (
    val date: Date = Date(),
    val amount: Int = 0,
        )