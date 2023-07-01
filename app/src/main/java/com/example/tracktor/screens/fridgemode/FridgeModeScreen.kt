package com.example.tracktor.screens.fridgemode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.FRIDGE_MODE_SCREEN
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar

@Composable
fun FridgeModeScreen(openScreen: (String)->Unit,clearAndNavigate:(String)->Unit, viewModel: FridgeModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    FridgeModeScreenContent(
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate)
    )
}



@Composable
fun FridgeModeScreenContent(
    bottomNavActions:List<()->Unit>,
    uiState:FridgeModeUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>
)
{

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View local fridges!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f)){
            Text("Coming Soon")
        }
        NavBarComposable(FRIDGE_MODE_SCREEN, bottomNavActions)

    }
}
