package com.example.tracktor.screens.splash

import com.example.tracktor.LOGIN_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.SPLASH_SCREEN
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    userManagerRepository: UserManagerRepository,
) : TracktorViewModel(userManagerRepository) {
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        if (userManagerRepository.loggedIn) openAndPopUp(SELECT_FARM_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }
}