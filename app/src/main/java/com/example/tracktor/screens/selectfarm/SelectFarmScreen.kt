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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.common.composable.CreateFarmButton
import com.example.tracktor.model.Farm

@Composable
fun SelectFarmScreen(openScreen: (String) -> Unit, viewModel: SelectFarmViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    SideEffect {
        viewModel.retrieveFarms()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.farms.forEach { farm ->
                Button(onClick = {
                    viewModel.onFarmNameClick(openScreen,farm!!.id)
                }) {
                    Text(text = farm!!.name)
                }
            }
        }
        Column(modifier = Modifier.padding(30.dp),verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End){
            CreateFarmButton(action = { viewModel.onCreateFarmClick(openScreen) })
        }
    }
}
@Preview
@Composable
fun SelectFarmScreenPreview() {
    SelectFarmScreen(openScreen = {}, viewModel = hiltViewModel())
}