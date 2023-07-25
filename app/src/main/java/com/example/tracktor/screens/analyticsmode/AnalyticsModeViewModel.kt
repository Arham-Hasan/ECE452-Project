package com.example.tracktor.screens.analyticsmode

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.DatePickedAmount
import com.example.tracktor.data.model.Inventory
import com.example.tracktor.data.model.UserTransaction
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AnalyticsModeViewModel @Inject constructor(
    val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(AnalyticsModeUiState())
        private set

    private lateinit var userInventory: Inventory


    private var userId: String = ""

    private var itemName: String = ""

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended


    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun toggleDropDown() {
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onUserDropDownSelect(user: String) {
        userId = user
        onCreateChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onItemDropDownSelect(item: String) {
        itemName = item
        onCreateChart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onCreateChart() {
        uiState.value = uiState.value.copy(totalItemSold = 0, totalItemRevenue = 0.0)

        if (itemName == "" || userId == "" || itemName == "Please Select" || userId == "Please Select") {
            SnackbarManager.showMessage("Please Select a user and an item from the dropdown".toSnackbarMessage())

            return
        }

        val itemHistoryMap = mutableMapOf<String, Long>()

        val itemEntry = userInventory.itemMap[itemName] as HashMap<String, Any>

        if (itemEntry.isEmpty()) {
            return
        }

        val userStats = itemEntry["userStats"] as? MutableMap<*, *> ?: return
        val userStat = userStats[userId] as Map<*, *>?
        if (userStat != null) {
            val pickList = userStat["pickList"] as List<*>
            if (pickList.isNotEmpty()) {
                var currentAmount: Long = 0
                var currentDate = dateFormat.format(parseTransaction(pickList[0] as Map<*, *>).date)
                pickList.forEach { pick ->
                    val transaction = pick as Map<*, *>
                    val userTransaction = parseTransaction(transaction)
                    if (isSameDay(currentDate, userTransaction.date)) {
                        currentAmount += userTransaction.amount.toLong()
                        println("SAME DAY $currentAmount, $currentDate")
                    } else {
                        val datePickedAmount = DatePickedAmount(currentDate, currentAmount)
                        itemHistoryMap[datePickedAmount.date] = datePickedAmount.amount

                        currentDate = dateFormat.format(userTransaction.date)
                        currentAmount = userTransaction.amount.toLong()
                    }
                }

                val datePickedAmount = DatePickedAmount(currentDate, currentAmount)
                itemHistoryMap[datePickedAmount.date] = datePickedAmount.amount
            }

            uiState.value = uiState.value.copy(
                totalItemSold = userStat["sellTotal"] as Long,
                totalItemRevenue = userStat["revenueTotal"] as Double
            )


        }
        Log.i("Analytics", "Pick Stats for $userId: $itemHistoryMap")



        uiState.value = uiState.value.copy(dataMap = padDates(itemHistoryMap))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun padDates(itemMap: MutableMap<String, Long>): Map<String, Long> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        if (itemMap.size < 5) {
            val today = LocalDate.now()
            var dayCounter = 0
            for (i in 0 until (5 - itemMap.size)) {
                var candidateDate = today.minusDays(dayCounter.toLong()).format(formatter)
                while (itemMap[candidateDate] != null) {
                    dayCounter += 1
                    candidateDate = today.minusDays(dayCounter.toLong()).format(formatter)
                }
                itemMap[candidateDate] = 0L
            }
        }

        val sortedKeys = itemMap.keys.map { LocalDate.parse(it, formatter) }.sorted()

        return sortedKeys.associate { it.format(formatter) to itemMap.getValue(it.format(formatter)) }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun isSameDay(date1: String, date2: Date): Boolean {
        val dateString2 = dateFormat.format(date2)

        return date1 == dateString2
    }

    private fun parseTransaction(transaction: Map<*, *>): UserTransaction {
        val date = transaction["date"] as Timestamp
        val amount = transaction["amount"] as Long

        return UserTransaction(date.toDate(), amount.toInt())
    }

    fun getUsersAndInventory() {
        launchCatching {
            val users = farmManagerRepository.getFarmUsers()
            if (!users.isNullOrEmpty()) {
                val userNameList =
                    users.map { user -> userManagerRepository.getUserName(user.userId) }
                uiState.value = uiState.value.copy(userList = userNameList)
                Log.i("Analytics", "User on Farm: " + uiState.value.userList.toString())
            }


            val inventory = farmManagerRepository.getInventory()
            userInventory = inventory
            Log.i("Analytics", "Farm Inventory: $userInventory")

            val itemList = inventory.itemMap.keys.toList()
            uiState.value = uiState.value.copy(itemList = itemList)

        }

    }


}