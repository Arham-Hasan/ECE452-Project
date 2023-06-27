package com.example.tracktor.screens.inventorymode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable

@Composable
fun InventoryModeScreen(openScreen: (String)->Unit, viewModel: InventoryModeViewModel = hiltViewModel()) {

    InventoryModeScreenContent(
        { viewModel.onPickingClick(openScreen)},
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onAnalyticsClick(openScreen)},
    )
}



@Composable
fun InventoryModeScreenContent(
    onPickingClick: () -> Unit,
    onSellingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onAnalyticsClick: () -> Unit)
{
    BasicToolbar("View your Inventory!")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    }
    NavBarComposable(INVENTORY_MODE_SCREEN, onPickingClick,onSellingClick,onFridgeClick,onAnalyticsClick,{})
}
