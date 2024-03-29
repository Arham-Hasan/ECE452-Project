package com.example.tracktor.screens.inventorymode

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.composable.CreateItemButton
import com.example.tracktor.common.composable.ModifyItem
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar
import com.example.tracktor.common.composable.ProductCard
import com.example.tracktor.data.model.InventoryItem

@Composable
fun InventoryModeScreen(openScreen: (String)->Unit, clearAndNavigate:(String)->Unit,viewModel: InventoryModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    InventoryModeScreenContent(
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate),
        { viewModel.addItemToInventory(openScreen) },
        viewModel::onItemSave,
    )
    LaunchedEffect(viewModel) { viewModel.retrieveItems() }
}



@Composable
fun InventoryModeScreenContent(
    bottomNavBarActions: List<() -> Unit>,
    uiState: InventoryModeUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>,
    addItemToInventory: () ->Unit,
    onItemSave: (String, String, Uri, InventoryItem) -> Unit
    )
{

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionsToolbar(
            title = "View your Inventory!",
            dropDownExtended = uiState.dropDrownExtended,
            toggleDropDown = toggleDropDown,
            dropDownOptions = dropDownOptions
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var items by remember { mutableStateOf(uiState.items) }

            LaunchedEffect(uiState.items) {
                items = uiState.items
            }

            var images by remember { mutableStateOf(uiState.imageMap) }

            LaunchedEffect(uiState.imageMap) {
                images = uiState.imageMap
            }

            if(items.isEmpty()){
                androidx.compose.material3.Text(text = "Please Create an Item")
            }
            else {
                androidx.compose.material3.Text(text = "Select Item")
                items.forEach { item ->
                    val editPopup = remember { mutableStateOf(false)}
                    ProductCard(
                        action = {
                            editPopup.value = !editPopup.value
                        },
                        imageBitmap = images[item]!!,
                        name = item.name,
                        price = item.itemPrice,
                        quantity = item.itemTotal
                    )
                    ModifyItem(
                        toggleAlert = {editPopup.value = !editPopup.value},
                        AlertVisible = editPopup.value,
                        item = item,
                        onSave = {price, quantity, imageUri ->
                            onItemSave(price, quantity, imageUri, item)
                         },
                    )
                }
            }

        }

        Column(modifier = Modifier.padding(30.dp),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End){
            CreateItemButton(action = addItemToInventory)
        }
        NavBarComposable(INVENTORY_MODE_SCREEN, bottomNavBarActions)

    }


}

