package com.example.tracktor.screens.pickingmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.PICKING_MODE_SCREEN
import androidx.compose.ui.platform.LocalContext
import com.example.tracktor.TracktorActivity
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
@Composable
fun PickingModeScreen(openScreen: (String)->Unit, viewModel: PickingModeViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val speechContext = context as TracktorActivity

    PickingModeScreenContent(
        { speechContext.onMicButtonClick(context) },
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onAnalyticsClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)}
    )

    LaunchedEffect(speechContext.speechInput.value){
        viewModel.parseInput(speechContext.speechInput.value)

//        resets value of input so its not used again
        speechContext.speechInput.value = ""
    }

    }

@Composable
fun PickingModeScreenContent(
    onMicButtonClick: () -> Unit,
    onSellingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onInventoryClick: () -> Unit)
{


    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar("Please press the button and start picking!")
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center){
            MicButton("Start Picking", Modifier, action = onMicButtonClick)
        }
        NavBarComposable(PICKING_MODE_SCREEN, {},onSellingClick,onFridgeClick,onAnalyticsClick,onInventoryClick)
    }

}
