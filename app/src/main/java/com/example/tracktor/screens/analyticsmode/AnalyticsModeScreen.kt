package com.example.tracktor.screens.analyticsmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable

@Composable
fun AnalyticsModeScreen(openScreen: (String)->Unit, viewModel: AnalyticsModeViewModel = hiltViewModel()) {

    AnalyticsModeScreenContent(
        { viewModel.onPickingClick(openScreen)},
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)}
    )
}



@Composable
fun AnalyticsModeScreenContent(
    onPickingClick: () -> Unit,
    onSellingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onInventoryClick: () -> Unit)
{
    BasicToolbar("View your analytics!")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {}
    NavBarComposable(
        ANALYTICS_MODE_SCREEN, onPickingClick,
        onSellingClick,onFridgeClick,{},onInventoryClick)
}
