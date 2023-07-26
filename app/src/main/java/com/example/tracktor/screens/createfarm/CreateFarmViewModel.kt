package com.example.tracktor.screens.createfarm

import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_FARM_SCREEN
import com.example.tracktor.SELECT_FARM_SCREEN
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CreateFarmViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository) {
    var uiState = mutableStateOf(CreateFarmUiState())
        private set

    private val name
        get() = uiState.value.name

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onCreateFarmClick(openAndPopUp: (String, String) -> Unit) {

        if (name.isBlank()) {
            SnackbarManager.showMessage("Please create a farm name".toSnackbarMessage())
            return
        }
        launchCatching {
            SnackbarManager.showMessage("Creating Farm".toSnackbarMessage())
            delay(500)
            farmManagerRepository.createFarm(name = uiState.value.name)
            openAndPopUp(SELECT_FARM_SCREEN, CREATE_FARM_SCREEN)
        }
    }
}