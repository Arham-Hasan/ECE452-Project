package com.example.tracktor.screens.inventorymode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar
import com.example.tracktor.screens.fridgemode.FridgeModeUiState

@Composable
fun InventoryModeScreen(openScreen: (String)->Unit, clearAndNavigate:(String)->Unit,viewModel: InventoryModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    InventoryModeScreenContent(
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate)
    )
}



@Composable
fun InventoryModeScreenContent(
    bottomNavBarActions: List<() -> Unit>,
    uiState: InventoryModeUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>
)
{


    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View your Inventory!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f)){
            Text("Coming Soon")
        }
        NavBarComposable(INVENTORY_MODE_SCREEN, bottomNavBarActions)

    }
}
