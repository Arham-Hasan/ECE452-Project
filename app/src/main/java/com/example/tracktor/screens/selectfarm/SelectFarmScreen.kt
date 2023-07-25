package com.example.tracktor.screens.selectfarm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.common.composable.CreateFarmButton
import com.example.tracktor.common.composable.ExpandableButton
import com.example.tracktor.common.composable.JoinFarmButton
import com.example.tracktor.common.composable.OptionsToolbar
import com.example.tracktor.data.model.Farm
import kotlin.reflect.KFunction2

@Composable
fun SelectFarmScreen(openScreen: (String) -> Unit, clearAndNavigate:(String)->Unit,viewModel: SelectFarmViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    SelectFarmScreenContent(
        uiState = uiState,
        toggleDropDown = { viewModel.toggleDropDown() },
        dropDownOptions = viewModel.dropDownActionsBeforeFarmSelected(openScreen, clearAndNavigate),
        openScreen = openScreen,
        onCreateFarmClick = {viewModel.onCreateFarmClick(openScreen)},
        onSelectFarmClick = viewModel::onFarmNameClick,
        onJoinFarmClick = {viewModel.onJoinFarmClick(openScreen)}
    )
    LaunchedEffect(viewModel) { viewModel.retrieveFarms() }
}
@Composable
fun SelectFarmScreenContent(
    uiState:SelectFarmUiState,
    toggleDropDown: ()->Unit,
    dropDownOptions: List<Pair<String,()->Unit>>,
    openScreen: (String) -> Unit,
    onCreateFarmClick: ()->Unit,
    onSelectFarmClick: KFunction2<(String) -> Unit, Farm, Unit>,
    onJoinFarmClick: ()->Unit
){
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(){
            OptionsToolbar(
                title = "Select a Farm",
                dropDownExtended = uiState.dropDrownExtended,
                toggleDropDown = toggleDropDown,
                dropDownOptions = dropDownOptions)
        }
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(uiState.farms.isEmpty()){
                Text(text = "Please Join or Create a Farm")
            }
            else {
                Text(text = "Select Farm")
                uiState.farms.forEach { farm ->
                    Button(onClick ={onSelectFarmClick(openScreen, farm!!)}

                    ) {
                        Text(text = farm!!.name)
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(30.dp),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End){
            if (expanded) {
                CreateFarmButton(action = onCreateFarmClick)
                JoinFarmButton(action = onJoinFarmClick)
            }
            ExpandableButton(action = {expanded = !expanded}, expanded = expanded)
        }
    }
}
@Preview
@Composable
fun SelectFarmScreenPreview() {
    fun a(b:(String)->Unit,c:Farm){}
    SelectFarmScreenContent(SelectFarmUiState(),{},listOf(),{},{},::a,{})
}