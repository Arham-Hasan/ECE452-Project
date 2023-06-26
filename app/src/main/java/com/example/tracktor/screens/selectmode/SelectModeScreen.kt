package com.example.tracktor.screens.selectmode

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.SIGN_UP_SCREEN
import com.example.tracktor.TracktorAppState

@Composable
fun SelectModeScreen(navigate: (String) -> Unit) {
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
                onClick = {navigate(PICKING_MODE_SCREEN)},
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


