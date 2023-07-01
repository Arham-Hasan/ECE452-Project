package com.example.tracktor.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.LOGIN_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.SIGN_UP_SCREEN
import com.example.tracktor.common.functions.isValidEmail
import com.example.tracktor.common.functions.isValidPassword
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    accountService: AccountService,
) : TracktorViewModel(accountService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue:String){
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue:String){
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {

        if(!email.isValidEmail()){
            SnackbarManager.showMessage("Invalid Email".toSnackbarMessage())
            return
        }
        if(!password.isValidPassword()){
            SnackbarManager.showMessage("Incorrect Password".toSnackbarMessage())
            return
        }
        launchCatching{
            accountService.authenticate(email, password)
            openAndPopUp(SELECT_FARM_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onSignUpClick(openScreen: (String) -> Unit){
//        SnackbarManager.showMessage("signup".toSnackbarMessage())
        openScreen(SIGN_UP_SCREEN)
    }

    fun onForgotPasswordClick(openScreen: (String) -> Unit){
        openScreen(SIGN_UP_SCREEN)
    }

}