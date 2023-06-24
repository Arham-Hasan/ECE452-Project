package com.example.tracktorapp.screens.selectMode

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SelectModeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Farming")
            }

            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Market")
            }

            Button(
                onClick = {},
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Donating")
            }
        }
    }
}

@Preview
@Composable
fun SelectModeScreenPreview(){
    SelectModeScreen(navController = rememberNavController())
}