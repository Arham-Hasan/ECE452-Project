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
import com.example.tracktor.ANALYTICS_MODE_SCREEN
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar


@Composable
fun AnalyticsModeScreen(openScreen: (String)->Unit, clearAndNavigate:(String)->Unit, viewModel: AnalyticsModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState
    AnalyticsModeScreenContent(
        uiState,
        viewModel.bottomNavBarActions(openScreen),
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate)
    )
}



@Composable
fun AnalyticsModeScreenContent(
    uiState: AnalyticsModeUiState,
    bottomNavActions:List<()->Unit>,
    toggleDropDown: () -> Unit,
    dropDownOptions: List<Pair<String,()->Unit>>
    )
{


    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View your analytics!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f)){
            Text("Coming Soon")
        }
        NavBarComposable(
            ANALYTICS_MODE_SCREEN, bottomNavActions)

    }

}
