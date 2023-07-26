package com.example.tracktor.screens.analyticsmode

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.Inventory
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class AnalyticsModeViewModel @Inject constructor(   val farmManagerRepository: FarmManagerRepository,
                                                    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(AnalyticsModeUiState())
        private set

    private lateinit var userInventory: Inventory


    private val itemList
        get() = uiState.value.itemList
    private val userList
        get() = uiState.value.userList

    private val itemName
        get() = uiState.value.itemName

    private val userId
        get() = uiState.value.userId


    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended


    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun toggleDropDown(){
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUserDropDownSelect(user: String) {
        uiState.value = uiState.value.copy(userId = user)
        onCreateChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onItemDropDownSelect(item: String) {
        uiState.value = uiState.value.copy(itemName = item)
        onCreateChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onCreateChart() {
        uiState.value = uiState.value.copy(xAxis=last5days())

        if(itemName == "" || userId == "" || itemName == "Please Select" || userId == "Please Select"){
            SnackbarManager.showMessage("Please Select a user and an item from the dropdown".toSnackbarMessage())

            return
        }

        val values = getItem(itemName,userId)
        println(values)
        uiState.value = uiState.value.copy(
            SellLast5arr= values.sellStats.last5arr,
            SellLast5total=values.sellStats.last5total,
            SellLast5revenue=values.sellStats.last5revenue,
            SellAllTime=values.sellStats.allTime,
            SellAllTimeRevenue=values.sellStats.allTimeRevenue,
            PickLast5arr=values.pickStats.last5arr,
            PickLast5total=values.pickStats.last5total,
            PickAllTime=values.pickStats.allTime
        )

    }



    private fun parseTransaction(transaction: Map<*,*>): UserTransaction{
        val date = transaction["date"] as Timestamp
        val amount = transaction["amount"] as Long

        return UserTransaction(date.toDate(), amount.toInt())
    }
    private fun parseSellTransaction(transaction: Map<*,*>): UserTransaction {


        return parseTransaction(transaction["transaction"] as Map<*, *>)
    }

    fun getUsersAndInventory(){
        launchCatching {
            val users = farmManagerRepository.getFarmUsers()
            if(!users.isNullOrEmpty()){
                val userNameList = mutableListOf(Pair("All Users","All Users"))
                users.forEach { user -> userNameList.add(Pair(userManagerRepository.getUserName(user.userId),user.userId)) }
                uiState.value = uiState.value.copy(userList = userNameList)
                Log.i("Analytics","User on Farm: "+uiState.value.userList.toString())
            }



            val inventory = farmManagerRepository.getInventory()
            userInventory = inventory
            Log.i("Analytics", "Farm Inventory: $userInventory")

            val itemList = mutableListOf(Pair("All Items","All Items"))
            inventory.itemMap.keys.toList().forEach {x -> itemList.add(Pair(x,x))}
            uiState.value = uiState.value.copy(itemList = itemList)

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getItem(item:String, user: String):GraphStats{

        if(item == "All Items"){
            var graphStats = GraphStats()
            for(i in 1 until itemList.size) {
                val temp = getItem(itemList[i].second,user)
                graphStats = GraphStats(
                    sumPickStats(graphStats.pickStats,temp.pickStats),
                    sumSellStats(graphStats.sellStats,temp.sellStats)
                )
            }
            return graphStats
        }
        else{
            println(item)
            println(userInventory.itemMap)
            val itemEntry = userInventory.itemMap[item] as Map<*,*>
            val userStats = itemEntry["userStats"] as Map<*,*>
            return getUserItem(user,userStats, itemEntry["itemPrice"] as Double)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserItem(user:String, userStats:Map<*,*>, price:Double):GraphStats{
        if(user == "All Users"){
            var graphStats = GraphStats()

            for(i in 1 until userList.size) {
                val temp = getUserItem(userList[i].second, userStats,price)
                graphStats = GraphStats(
                    sumPickStats(graphStats.pickStats,temp.pickStats),
                    sumSellStats(graphStats.sellStats,temp.sellStats)
                )
            }
            return graphStats
        }else{
            if (!userStats.containsKey(user)){
                return GraphStats()
            }
            val userStatMap = userStats[user] as Map<*,*>
            val pick = getPick(userStatMap)
            val sell = getSell(userStatMap,price)
            return GraphStats(pick,sell)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getSell(userStat: Map<*,*>, price:Double):SellStats{
        val last5arr = getLast5Sell(userStat)
        val last5total = last5arr.sum()
        val last5revenue = last5total*price
        val allTime = userStat["sellTotal"] as? Long
        val allTimeTemp = allTime!!.toInt()
        val allTimeRevenue = allTimeTemp*price
        return SellStats(
            last5arr,
            last5total,
            last5revenue,
            allTimeTemp,
            allTimeRevenue
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLast5Sell(userStat: Map<*,*>):List<Int>{
        val currList = mutableListOf(0,0,0,0,0)
        val pickList = userStat["sellList"] as List<*>
        if(pickList.isEmpty()){
            return currList
        }
        var currentDate = LocalDate.now()
        var j = pickList.size-1
        for (i in 0 until 5) {
            while(j >= 0 && isSameDay(currentDate,parseSellTransaction(pickList[j] as Map<*, *>).date)){
                val transaction =  pickList[j] as Map<*, *>
                val userTransaction = parseSellTransaction(transaction)
                currList[i] += userTransaction.amount
                j-=1
            }
            currentDate = currentDate.minusDays(1.toLong())
        }
        return currList.reversed()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getPick(userStat: Map<*,*>):PickStats{
        val last5arr = getLast5Pick(userStat)
        val last5total = last5arr.sum()
        val allTime = userStat["pickTotal"] as? Long
        return PickStats(
            last5arr,
            last5total,
            allTime!!.toInt()
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLast5Pick(userStat: Map<*,*>):List<Int>{
        val currList = mutableListOf(0,0,0,0,0)
        val pickList = userStat["pickList"] as List<*>
        if(pickList.isEmpty()){
            return currList
        }
        var currentDate = LocalDate.now()
        var j = pickList.size-1
        for (i in 0 until 5) {
            while(j >= 0 && isSameDay(currentDate,parseTransaction(pickList[j] as Map<*, *>).date)){
                val transaction =  pickList[j] as Map<*, *>
                val userTransaction = parseTransaction(transaction)
                currList[i] += userTransaction.amount
                j-=1
            }
            currentDate = currentDate.minusDays(1.toLong())
        }
        return currList.reversed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isSameDay(date1: LocalDate, date2: Date):Boolean{
        return date1 == date2.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun last5days():List<String>{
        var currentDate = LocalDate.now().minusDays(4.toLong())
        val dateList = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("dd-MM")
        for(i in 0 until 5){
            dateList.add(currentDate.format(formatter))
            currentDate = currentDate.plusDays(1.toLong())
        }
        return dateList
    }
    fun sumArrays(arr1: List<Int>, arr2: List<Int>): List<Int> {
        return arr1.zip(arr2) { a, b -> a + b }.toList()
    }

    data class PickStats(
        val last5arr:List<Int> = listOf(0,0,0,0,0),
        val last5total:Int = 0,
        val allTime: Int = 0,
    )

    data class SellStats(
        val last5arr:List<Int> = listOf(0,0,0,0,0),
        val last5total:Int=0,
        val last5revenue:Double=0.0,
        val allTime:Int = 0,
        val allTimeRevenue:Double = 0.0,
    )

    data class GraphStats(
        val pickStats:PickStats = PickStats(),
        val sellStats:SellStats = SellStats()
    )

    fun sumPickStats(pickStats1: PickStats,pickStats2: PickStats):PickStats{
        return PickStats(
            sumArrays(pickStats1.last5arr,pickStats2.last5arr),
            pickStats1.last5total+pickStats2.last5total,
            pickStats1.allTime+pickStats2.allTime
        )
    }

    fun sumSellStats(sellStats1: SellStats, sellStats2: SellStats):SellStats{
        return SellStats(
            sumArrays(sellStats2.last5arr,sellStats1.last5arr),
            sellStats1.last5total+sellStats2.last5total,
            sellStats1.last5revenue+sellStats2.last5revenue,
            sellStats1.allTime+sellStats2.allTime,
            sellStats1.allTimeRevenue+sellStats2.allTimeRevenue
        )
    }

}