package com.example.tracktor.screens.analyticsmode

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.common.composable.AnalyticsCard
import com.example.tracktor.common.composable.BasicDropdown
import com.example.tracktor.common.composable.ChartComposable
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyticsModeScreen(
    openScreen: (String) -> Unit,
    clearAndNavigate: (String) -> Unit,
    viewModel: AnalyticsModeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    LaunchedEffect(viewModel) {
        viewModel.getUsersAndInventory()
    }

    AnalyticsModeScreenContent(
        uiState,
        viewModel.bottomNavBarActions(openScreen),
        { viewModel.toggleDropDown() },
        viewModel.dropDownActionsAfterFarmSelected(openScreen, clearAndNavigate),
        viewModel::onUserDropDownSelect,
        viewModel::onItemDropDownSelect,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnalyticsModeScreenContent(
    uiState: AnalyticsModeUiState,
    bottomNavActions: List<() -> Unit>,
    toggleDropDown: () -> Unit,
    dropDownOptions: List<Pair<String, () -> Unit>>,
    onUserDropDownSelect: (String) -> Unit,
    onItemDropDownSelect: (String) -> Unit,
) {
    val state = rememberScrollState()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View your analytics!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )

        Column(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .verticalScroll(state)
        ) {  // padding added to the content Column
            BasicDropdown(
                options = uiState.userList,
                action = onUserDropDownSelect,
                label = "User(s)"
            )
            BasicDropdown(
                uiState.itemList,
                onItemDropDownSelect,
                "Item(s)"
            )

            var data by remember { mutableStateOf(uiState.dataMap) }

            // LaunchedEffect to update 'data' when uiState.dataMap changes
            LaunchedEffect(uiState.dataMap) {
                data = uiState.dataMap
            }

<<<<<<< HEAD
            ChartComposable(uiState.PickLast5arr,"Sold",uiState.SellLast5arr,"Picked",uiState.xAxis)
=======
            ChartComposable(
                uiState.PickLast5arr,
                "Quantity Sold",
                uiState.SellLast5arr,
                "Quantity Picked",
                uiState.xAxis
            )
>>>>>>> 9c17fe369f8229baa7912de6b9e4ddb5758b510b
            AnalyticsCard("Total item(s) sold over past 5 days: ${uiState.SellLast5total}")
            AnalyticsCard("Total revenue over past 5 days: \$${uiState.SellLast5revenue}")
            AnalyticsCard("Total item(s) picked over past 5 days: ${uiState.PickLast5total}")
            AnalyticsCard("Total item(s) sold: ${uiState.SellAllTime}")
            AnalyticsCard("Total revenue: \$${uiState.SellAllTimeRevenue}")
            AnalyticsCard("Total item(s) picked: ${uiState.PickAllTime}")

        }
        NavBarComposable(
            ANALYTICS_MODE_SCREEN,
            bottomNavActions
        )
    }
}

