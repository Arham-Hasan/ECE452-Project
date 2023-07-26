package com.example.tracktor.screens.sellingmode

data class SellingModeUiState(
    val dropDrownExtended: Boolean = false,
    val validItems: Set<String> = setOf(),
    val transactions: MutableMap<String, Int> = mutableMapOf<String, Int>(),
    val itemToPriceMap: Map<String, Double> = mapOf<String, Double>(),
    val itemToQuantityMap: MutableMap<String, Long> = mutableMapOf<String, Long>()

)