package com.example.tracktor.screens.selectfarm
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_FARM_SCREEN
import com.example.tracktor.FARM_ID
import com.example.tracktor.SELECT_MODE_SCREEN
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SelectFarmViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    authRepository: AuthRepository
)
    : TracktorViewModel(authRepository) {

    var uiState = mutableStateOf(SelectFarmUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    init {
        runBlocking {
            uiState.value =
                uiState.value.copy(farms = farmManagerRepository.getFarms())
        }
    }

    fun retrieveFarms() {
        // Simulated data retrieval
        // return listOf(Farm(id="1", name = "Heeko farm"), Farm(id="2", name = "Boge farm"), Farm(id="3", name = "Arham farm"))
        runBlocking {
            uiState.value =
                uiState.value.copy(farms = farmManagerRepository.getFarms())
        }
    }

    fun onFarmNameClick(openScreen: (String) -> Unit, farm_id: String) {
        openScreen("$SELECT_MODE_SCREEN?$FARM_ID=${farm_id}")
    }

    fun onCreateFarmClick(openScreen: (String) -> Unit) {
        openScreen(CREATE_FARM_SCREEN)
    }


    fun toggleDropDown() {
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }
}
