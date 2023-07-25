package com.example.tracktor.screens.login

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
import com.example.tracktor.common.composable.BasicTextButton
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.EmailField
import com.example.tracktor.common.composable.PasswordField
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState,
        viewModel::onEmailChange,
        viewModel::onPasswordChange,
        { viewModel.onSignInClick(openAndPopUp) },
        { viewModel.onSignUpClick(openScreen) },
        {}
    )

}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    BasicToolbar("Please enter your login details")

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(value = uiState.email, onNewValue = onEmailChange)
        PasswordField(value = uiState.password, onNewValue = onPasswordChange)
        BasicButton("Sign In", Modifier, action = onSignInClick)
        BasicTextButton("Create an Account", Modifier, action = onCreateAccountClick)
        BasicTextButton("Forgot Password?", Modifier, action = onForgotPasswordClick)
    }
}

@Preview
@Composable
fun LoginScreenContentPreview() {
    TracktorTheme {
        LoginScreenContent(
            uiState = LoginUiState(),
            onEmailChange = {},
            onPasswordChange = {},
            onSignInClick = {},
            onCreateAccountClick = {},
            onForgotPasswordClick = {}
        )
    }

}


