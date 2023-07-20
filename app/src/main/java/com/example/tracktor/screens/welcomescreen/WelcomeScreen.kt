package com.example.tracktor.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.tracktor_logo),
//            contentDescription = "App logo",
//            modifier = Modifier
//                .height(150.dp)
//                .width(150.dp)
//        )


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onLoginClick() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSignUpClick() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            Text(text = "Sign Up")
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    TracktorTheme {
        WelcomeScreen(
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}
