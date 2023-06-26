package com.example.tracktor.screens.selectfarm

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.model.Farm

@Composable
fun SelectFarmScreen(openAndPopUp: (String, String) -> Unit, openScreen: (String) -> Unit, viewModel: SelectFarmViewModel = hiltViewModel()) {
    val farms: List<Farm> = viewModel.farms

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        farms.forEach { item ->
            Text(
                text = item.name,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable{}
                    .fillMaxSize()
            )
        }
    }
}
