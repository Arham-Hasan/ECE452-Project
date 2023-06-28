package com.example.tracktor.screens.analyticsmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.ThreeOptionToolbar

@Composable
fun AnalyticsModeScreen(openScreen: (String)->Unit, viewModel: AnalyticsModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState
    AnalyticsModeScreenContent(
        uiState,
        { viewModel.onPickingClick(openScreen)},
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)},
        { viewModel.toggleDropDown() },
        {viewModel.onSignOutClick()},
        {viewModel.onSettingsClick()},
        {viewModel.onChangeFarmClick(openScreen)}
    )
}



@Composable
fun AnalyticsModeScreenContent(
    uiState: AnalyticsModeUiState,
    onPickingClick: () -> Unit,
    onSellingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onInventoryClick: () -> Unit,
    toggleDropDown: () -> Unit,
    onSignOutClick: () -> Unit,
    onSettingsClick:() -> Unit,
    onChangeFarmClick:() -> Unit
    )
{


    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThreeOptionToolbar(
            title = "View your analytics!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            firstTitle = "Settings",
            firstAction = onSettingsClick,
            secondTitle = "Change Farms",
            secondAction = onChangeFarmClick,
            thirdTitle = "Sign Out",
            thirdAction = onSignOutClick
        )
        Column(Modifier.weight(1f)){
            Text("Coming Soon")
        }
        NavBarComposable(
            ANALYTICS_MODE_SCREEN, onPickingClick,
            onSellingClick,onFridgeClick,{},onInventoryClick)

    }

}
