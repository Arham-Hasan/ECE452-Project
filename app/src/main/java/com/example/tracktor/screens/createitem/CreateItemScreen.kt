package com.example.tracktor.screens.createitem

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
fun CreateItemScreen(openAndPopUp: (String, String) -> Unit, viewModel: CreateItemViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    CreateItemScreenContent(
        uiState,
        viewModel::onNameChange,
    ) { viewModel.onCreateItemClick(openAndPopUp) }

}

@Composable
fun CreateItemScreenContent(
    uiState: CreateItemUiState,
    onNameChange : (String)->Unit,
    onCreateFarmClick: ()-> Unit,
) {
    BasicToolbar("Create an Item")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(text = "Item Name", value = uiState.name, onNewValue = onNameChange)
        BasicButton("Create an Item", Modifier, action = onCreateFarmClick)
    }
}


@Preview
@Composable
fun CreateItemScreenPreview(){
    TracktorTheme {
        CreateItemScreenContent(CreateItemUiState(),{},{})

    }
}
