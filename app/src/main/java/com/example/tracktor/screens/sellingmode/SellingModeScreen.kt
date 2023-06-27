package com.example.tracktor.screens.sellingmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable

@Composable
fun SellingModeScreen(openScreen: (String)->Unit, viewModel: SellingModeViewModel = hiltViewModel()) {

    SellingModeScreenContent(
        { viewModel.onPickingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onAnalyticsClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)},
        { viewModel.onMicButtonClick() }
    )
}



@Composable
fun SellingModeScreenContent(
    onMicButtonClick: () -> Unit,
    onPickingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onInventoryClick: () -> Unit)
{
    BasicToolbar("Please press the button and start selling!")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MicButton("Start Selling", Modifier, action = onMicButtonClick)
    }
    NavBarComposable(SELLING_MODE_SCREEN, onPickingClick,{},onFridgeClick,onAnalyticsClick,onInventoryClick)
}
