package com.example.tracktor.screens.analyticsmode

data class AnalyticsModeUiState (
    val dropDrownExtended: Boolean = false,
    val userList: List<String> = listOf<String>("Select User"),
    val itemList: List<String> = listOf<String>("Select Item"),
    val dataMap: Map<String, Long> = mapOf<String, Long>(),
    val totalItemRevenue: Double = 0.0,
    val totalItemSold: Long = 0

)