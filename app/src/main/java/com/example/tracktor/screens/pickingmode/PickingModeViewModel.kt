package com.example.tracktor.screens.pickingmode

import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.model.service.AccountService
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickingModeViewModel @Inject constructor() : TracktorViewModel() {
    fun onMicButtonClick(){
        SnackbarManager.showMessage("Listening".toSnackbarMessage())
    }
}