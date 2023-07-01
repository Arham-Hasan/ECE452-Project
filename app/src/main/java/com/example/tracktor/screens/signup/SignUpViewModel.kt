package com.example.tracktor.screens.signup

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.SELECT_MODE_SCREEN
import com.example.tracktor.SIGN_UP_SCREEN
import com.example.tracktor.common.functions.isValidEmail
import com.example.tracktor.common.functions.isValidPassword
import com.example.tracktor.common.functions.passwordsMatch
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(accountService: AccountService) : TracktorViewModel(accountService ) {
    var uiState = mutableStateOf(SignUpUiState())
        private set


    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    private val second_password
        get() = uiState.value.second_password

    private val name
        get() = uiState.value.name

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSecondPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(second_password = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage("Invalid Email".toSnackbarMessage())
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage("Password needs to be at least 8 characters".toSnackbarMessage())
            return
        }

        if (!password.passwordsMatch(second_password)) {
            SnackbarManager.showMessage("Passwords do not match".toSnackbarMessage())
            return
        }

        launchCatching {
            accountService.signUp(email, password)
            openAndPopUp(SELECT_FARM_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun onLoginClick(PopUp : ()->Unit){
        PopUp()
    }

}
