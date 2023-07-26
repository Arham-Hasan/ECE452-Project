package com.example.tracktor.screens.createfarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.common.composable.BasicButton
import com.example.tracktor.common.composable.BasicTextField
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun CreateFarmScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: CreateFarmViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState

    CreateFarmScreenContent(
        uiState,
        viewModel::onNameChange,
        { viewModel.onCreateFarmClick(openAndPopUp) },
    )

}

@Composable
fun CreateFarmScreenContent(
    uiState: CreateFarmUiState,
    onNameChange: (String) -> Unit,
    onCreateFarmClick: () -> Unit,
) {
    BasicToolbar("Create a Farm")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(text = "Farm Name", value = uiState.name, onNewValue = onNameChange)
        BasicButton("Create Farm", Modifier, action = onCreateFarmClick)
    }
}


@Preview
@Composable
fun CreateFarmScreenPreview() {
    TracktorTheme {
        CreateFarmScreenContent(CreateFarmUiState(), {}, {})

    }
}
