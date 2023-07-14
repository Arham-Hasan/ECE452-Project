package com.example.tracktor.screens.joinfarm

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
fun JoinFarmScreen(openAndPopUp: (String, String) -> Unit, viewModel: JoinFarmViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    CreateFarmScreenContent(
        uiState,
        viewModel::onNameChange,
        {viewModel.onJoinFarmClick(openAndPopUp)},
    )

}

@Composable
fun CreateFarmScreenContent(
    uiState: JoinFarmUiState,
    onNameChange : (String)->Unit,
    onCreateFarmClick: ()-> Unit,
) {
    BasicToolbar("Join a Farm")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(text = "Enter the Farm UUID", value = uiState.farmId, onNewValue = onNameChange)
        BasicButton("Join Farm", Modifier, action = onCreateFarmClick)
    }
}


@Preview
@Composable
fun CreateFarmScreenPreview(){
    TracktorTheme {
        CreateFarmScreenContent(JoinFarmUiState(),{},{})

    }
}
