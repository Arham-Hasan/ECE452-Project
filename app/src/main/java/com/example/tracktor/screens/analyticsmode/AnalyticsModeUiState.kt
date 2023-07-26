package com.example.tracktor.screens.analyticsmode

data class AnalyticsModeUiState (
    val dropDrownExtended: Boolean = false,
    val userList: List<Pair<String,String>> = listOf<Pair<String,String>>(Pair("All Users","All Users")),
    val itemList: List<Pair<String,String>> = listOf<Pair<String,String>>(Pair("All Items","All Items")),
    val dataMap: Map<String, Long> = mapOf<String, Long>(),
    val totalItemRevenue: Double = 0.0,
    val totalItemSold: Long = 0,
    val totalItemRevenueLast5days: Double = 0.0,
    val totalItemSoldLast5days: Long = 0,
    val SellLast5arr:List<Int> =listOf<Int>(0,0,0,0,0),
    val SellLast5total:Int=0,
    val SellLast5revenue:Double=0.0,
    val SellAllTime:Int = 0,
    val SellAllTimeRevenue:Double = 0.0,
    val PickLast5arr:List<Int> = listOf<Int>(0,0,0,0,0),
    val PickLast5total:Int = 0,
    val PickAllTime:Int = 0,
    val userId: String = "",
    val itemName: String = "",
    val xAxis: List<String> = listOf("","","","","")

)