package com.example.tracktor.screens.pickingmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.TracktorActivity
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar

@Composable
fun PickingModeScreen(openScreen: (String)->Unit,clearAndNavigate:(String)->Unit, viewModel: PickingModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    val context = LocalContext.current
    val speechContext = context as TracktorActivity
    PickingModeScreenContent(
        { speechContext.onMicButtonClick(context) },
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate)
    )

    LaunchedEffect(speechContext.speechInput.value){
        viewModel.parseInput(speechContext.speechInput.value)

//        resets value of input so its not used again
        speechContext.speechInput.value = ""
    }
    LaunchedEffect(viewModel) { viewModel.retrieveItems() }
    DisposableEffect(viewModel) {
        onDispose {viewModel.saveTransactions()}
    }
    }

@Composable
fun PickingModeScreenContent(
    onMicButtonClick: () -> Unit,
    bottomNavBarActions: List<() -> Unit>,
    uiState: PickingModeUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>
)
{


    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "Press the button and start picking!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center){
            MicButton("Start Picking", Modifier, action = onMicButtonClick)
        }
        NavBarComposable(PICKING_MODE_SCREEN, bottomNavBarActions)
    }

}
