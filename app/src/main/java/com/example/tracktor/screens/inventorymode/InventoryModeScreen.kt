package com.example.tracktor.screens.inventorymode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.INVENTORY_MODE_SCREEN
import com.example.tracktor.common.composable.CreateItemButton
import com.example.tracktor.common.composable.NavBarComposable
import com.example.tracktor.common.composable.OptionsToolbar
import com.example.tracktor.data.model.InventoryItem
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KFunction2

@Composable
fun InventoryModeScreen(openScreen: (String)->Unit, clearAndNavigate:(String)->Unit,viewModel: InventoryModeViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    InventoryModeScreenContent(
        viewModel.bottomNavBarActions(openScreen),
        uiState,
        {viewModel.toggleDropDown()},
        viewModel.dropDownActionsAfterFarmSelected(openScreen,clearAndNavigate),
        { viewModel.addItemToInventory(openScreen) },
        viewModel::onSelectItemClick,
        openScreen
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
    onSelectItemClick: KFunction2<(String) -> Unit, String, Unit>,
    openScreen: (String) -> Unit,
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
        if(uiState.items!!.isEmpty()){
            androidx.compose.material3.Text(text = "Please Create an Item")
        }
        else {
            androidx.compose.material3.Text(text = "Select Item")
            uiState.items?.forEach { item ->
                Button(onClick ={onSelectItemClick(openScreen, item)}

                ) {
                    androidx.compose.material3.Text(text = item!!)
                }
            }
        }
        Column(modifier = Modifier.padding(30.dp),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End){
            CreateItemButton(action = addItemToInventory)
        }
        NavBarComposable(INVENTORY_MODE_SCREEN, bottomNavBarActions)

    }


}

