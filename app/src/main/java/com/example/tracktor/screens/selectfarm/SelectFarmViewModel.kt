package com.example.tracktor.screens.selectfarm
import androidx.compose.runtime.mutableStateOf
import com.example.tracktor.CREATE_FARM_SCREEN
import com.example.tracktor.JOIN_FARM_SCREEN
import com.example.tracktor.PICKING_MODE_SCREEN
import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectFarmViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
)
    : TracktorViewModel(userManagerRepository) {

    var uiState = mutableStateOf(SelectFarmUiState())
        private set

    private val dropDrownExtended
        get() = uiState.value.dropDrownExtended

    init {
        farmManagerRepository.removeSelectedFarm()
    }

    fun retrieveFarms() {
        // Simulated data retrieval
        // return listOf(Farm(id="1", name = "Heeko farm"), Farm(id="2", name = "Boge farm"), Farm(id="3", name = "Arham farm"))
        launchCatching {
            uiState.value = uiState.value.copy(farms = farmManagerRepository.getActiveFarms())
        }
    }

    fun onFarmNameClick(openScreen: (String) -> Unit, farm: Farm) {
        farmManagerRepository.changeSelectedFarm(farm)
        openScreen(PICKING_MODE_SCREEN)
    }

    fun onCreateFarmClick(openScreen: (String) -> Unit) {
        openScreen(CREATE_FARM_SCREEN)
    }

    fun onJoinFarmClick(openScreen: (String) -> Unit) {
        openScreen(JOIN_FARM_SCREEN)
    }


    fun toggleDropDown() {
        uiState.value = uiState.value.copy(dropDrownExtended = !dropDrownExtended)
    }
}
