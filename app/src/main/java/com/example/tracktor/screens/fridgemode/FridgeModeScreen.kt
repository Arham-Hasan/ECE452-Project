package com.example.tracktor.screens.fridgemode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable

@Composable
fun FridgeModeScreen(openScreen: (String)->Unit, viewModel: FridgeModeViewModel = hiltViewModel()) {

    FridgeModeScreenContent(
        { viewModel.onPickingClick(openScreen)},
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onAnalyticsClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)},
    )
}



@Composable
fun FridgeModeScreenContent(
    onPickingClick: () -> Unit,
    onSellingClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onInventoryClick: () -> Unit)
{

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar("View local fridges!")
        Column(Modifier.weight(1f)){
            Text("Coming Soon")
        }
        NavBarComposable(FRIDGE_MODE_SCREEN, onPickingClick,onSellingClick,{},onAnalyticsClick,onInventoryClick)

    }
}
