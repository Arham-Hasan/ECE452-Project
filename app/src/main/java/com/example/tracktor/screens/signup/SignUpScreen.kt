package com.example.tracktor.screens.signup

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
import com.example.tracktor.common.composable.BasicTextField
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.EmailField
import com.example.tracktor.common.composable.PasswordField
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun SignUpScreen(clearAndNavigate: (String) -> Unit, popUp:()->Unit,  viewModel: SignUpViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    SignUpScreenContent(
        uiState,
        viewModel::onNameChange,
        viewModel::onEmailChange,
        viewModel::onPasswordChange,
        viewModel::onSecondPasswordChange,
        { viewModel.onSignUpClick(clearAndNavigate)},
        {viewModel.onLoginClick(popUp)}
    )

}

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onNameChange: (String)->Unit,
    onEmailChange: (String)->Unit,
    onPasswordChange: (String)->Unit,
    onSecondPasswordChange: (String)->Unit,
    onSignUpClick: ()->Unit,
    onLogInClick: ()->Unit
){
    BasicToolbar("Please enter your details")
    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BasicTextField("Name", uiState.name, onNameChange, "Name")
        EmailField(value = uiState.email, onNewValue = onEmailChange)
        PasswordField(value = uiState.password, onNewValue = onPasswordChange)
        PasswordField(value = uiState.second_password, onNewValue = onSecondPasswordChange)
        BasicButton("Sign Up", Modifier, action = onSignUpClick )
        BasicTextButton("Already have an account? Login", Modifier, action = onLogInClick )
    }
}

@Preview
@Composable
fun SignUpScreenContentPreview(){
    TracktorTheme {
        SignUpScreenContent(SignUpUiState(),{},{},{},{},{},{})
    }
}
