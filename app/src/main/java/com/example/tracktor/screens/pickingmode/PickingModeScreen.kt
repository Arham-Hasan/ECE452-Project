package com.example.tracktor.screens.pickingmode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.common.composable.BasicButton
import com.example.tracktor.common.composable.BasicTextButton
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.EmailField
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.PasswordField
import com.example.tracktor.screens.login.LoginUiState
import com.example.tracktor.screens.login.LoginViewModel
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun PickingModeScreen(openScreen: (String)->Unit, viewModel: PickingModeViewModel = hiltViewModel()) {

    PickingModeScreenContent(
        { viewModel.onMicButtonClick() },
        { viewModel.onSellingClick(openScreen)},
        { viewModel.onFridgesClick(openScreen)},
        { viewModel.onAnalyticsClick(openScreen)},
        { viewModel.onInventoryClick(openScreen)}
    )
}



@Composable
fun PickingModeScreenContent(
    onMicButtonClick: () -> Unit,
    onSellingClick: () -> Unit,
    onFridgeClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onInventoryClick: () -> Unit)
{
    BasicToolbar("Please press the button and start picking!")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MicButton("Start Picking", Modifier, action = onMicButtonClick)
    }
    NavBarComposable(PICKING_MODE_SCREEN, {},onSellingClick,onFridgeClick,onAnalyticsClick,onInventoryClick)
}
