package com.example.tracktor.screens.splash

import com.example.tracktor.LOGIN_SCREEN
import com.example.tracktor.SELECT_MODE_SCREEN
import com.example.tracktor.SPLASH_SCREEN
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
) : TracktorViewModel() {
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        if (accountService.loggedIn) openAndPopUp(SELECT_MODE_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }
}