package com.example.tracktor.screens.sellingmode

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.SELLING_MODE_SCREEN
import com.example.tracktor.TracktorActivity
import com.example.tracktor.common.composable.MicButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun SellingModeScreen(
    openScreen: (String) -> Unit,
    clearAndNavigate: (String) -> Unit,
    viewModel: SellingModeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    val context = LocalContext.current
    val speechContext = context as TracktorActivity

    SellingModeScreenContent(
        { speechContext.onMicButtonClick(context) },
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        { viewModel.toggleDropDown() },
        viewModel.dropDownActionsAfterFarmSelected(openScreen, clearAndNavigate)
    )

    LaunchedEffect(speechContext.speechInput.value) {
        viewModel.parseInput(speechContext.speechInput.value)

//        resets value of input so its not used again
        speechContext.speechInput.value = ""
    }
    LaunchedEffect(viewModel) { viewModel.retrieveItemsAndPrices() }
    DisposableEffect(viewModel) {
        onDispose { viewModel.saveTransactions() }
    }
}


@Composable
fun SellingModeScreenContent(
    onMicButtonClick: () -> Unit,
    bottomNavBarActions: List<() -> Unit>,
    uiState: SellingModeUiState,
    toggleDropDown: () -> Unit,
    dropDownOptions: List<Pair<String, () -> Unit>>
) {


    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "Press the button to start selling!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            MicButton("Start Selling", Modifier, action = onMicButtonClick)
        }

        NavBarComposable(SELLING_MODE_SCREEN, bottomNavBarActions)

    }

}

@Preview
@Composable
fun SellingModeScreenContentPreview() {
    TracktorTheme {
        SellingModeScreenContent(
            {},
            listOf({}, {}, {}, {}, {}),
            SellingModeUiState(),
            {},
            listOf()
        )
    }
}