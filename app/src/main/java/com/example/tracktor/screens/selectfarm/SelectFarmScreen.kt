package com.example.tracktor.screens.selectfarm

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.SELECT_MODE_SCREEN
import com.example.tracktor.model.Farm

@Composable
fun SelectFarmScreen(openScreen: (String) -> Unit, viewModel: SelectFarmViewModel = hiltViewModel()) {
    val farms: List<Farm> = viewModel.farms

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            farms.forEach { farm ->
                Button(onClick = {
                    viewModel.onFarmNameClick(openScreen,farm.id)
                }) {
                    Text(text = farm.name)
                }
            }
        }
    }
}
@Preview
@Composable
fun SelectFarmScreenPreview() {
    SelectFarmScreen(openScreen = {}, viewModel = hiltViewModel())
}