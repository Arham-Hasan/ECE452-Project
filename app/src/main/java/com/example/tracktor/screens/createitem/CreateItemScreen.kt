package com.example.tracktor.screens.createitem

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.tracktor.common.composable.NumberField
import com.example.tracktor.common.composable.BasicTextField
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun CreateItemScreen(openAndPopUp: (String, String) -> Unit, viewModel: CreateItemViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    CreateItemScreenContent(
        uiState,
        viewModel::onNameChange,
        {viewModel.onCreateItemClick(openAndPopUp)},
        viewModel::onPriceChange,
        viewModel::handleImageUpload
    )

}

@Composable
fun CreateItemScreenContent(
    uiState: CreateItemUiState,
    onNameChange: (String)->Unit,
    onCreateFarmClick: ()-> Unit,
    onPriceChange: (String)->Unit,
    handleImage: (Uri) -> Unit
) {

    BasicToolbar("Create an Item")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material3.Text(text = "Item Name")
        BasicTextField(text = "Apple", value = uiState.name, onNewValue = onNameChange, label = "Item Name")
        androidx.compose.material3.Text(text = "Item Price")
        NumberField(text = "1.23", value = uiState.price, onNewValue = onPriceChange, label = "Item Price", moneyField = true)
        val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                handleImage(uri)
            }
        }
        BasicButton("Select Item Image", Modifier, action = { pickImageLauncher.launch("image/*") })

        BasicButton("Create Item", Modifier, action = onCreateFarmClick)
    }
}


@Preview
@Composable
fun CreateItemScreenPreview(){
    TracktorTheme {
        CreateItemScreenContent(CreateItemUiState(),{},{},{},{})
    }
}
