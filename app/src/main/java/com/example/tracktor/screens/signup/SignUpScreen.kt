package com.example.tracktor.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.R
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
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.tracktor_logo),
            contentDescription = "App logo",
            modifier = Modifier
                .height(300.dp)
                .width(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField("Name", uiState.name, onNameChange, "Name")
        Spacer(modifier = Modifier.height(16.dp))

        EmailField(value = uiState.email, onNewValue = onEmailChange)
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(value = uiState.password, onNewValue = onPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(value = uiState.second_password, onNewValue = onSecondPasswordChange)
        Spacer(modifier = Modifier.height(16.dp))

        BasicButton("Sign Up", Modifier, action = onSignUpClick )
        Spacer(modifier = Modifier.height(16.dp))

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
