package com.example.tracktor.screens.signup

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.common.functions.isValidEmail
import com.example.tracktor.common.functions.isValidPassword
import com.example.tracktor.common.functions.passwordsMatch
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(userManagerRepository: UserManagerRepository) : TracktorViewModel(userManagerRepository ) {
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

    fun onSignUpClick(clearAndNavigate: (String) -> Unit) {
        if (name.isEmpty()) {
            SnackbarManager.showMessage("Please Enter a Name".toSnackbarMessage())
            return
        }
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
            userManagerRepository.signUp(name, email, password)
            clearAndNavigate(SELECT_FARM_SCREEN)
        }
    }

    fun onLoginClick(PopUp : ()->Unit){
        PopUp()
    }

}
