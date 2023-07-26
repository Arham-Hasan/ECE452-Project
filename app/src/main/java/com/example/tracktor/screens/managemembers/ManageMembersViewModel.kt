package com.example.tracktor.screens.managemembers

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import com.example.tracktor.data.model.FarmUserRelation
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageMembersViewModel @Inject constructor(
    private val farmManagerRepository: FarmManagerRepository,
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository)  {

    val currUserId = userManagerRepository.currentUserId
    val currFarmId = farmManagerRepository.getSelectedFarm()!!.id

    var uiState = mutableStateOf(ManageMembersUiState())
        private set

    fun acceptUser(user: FarmUserRelation) {
        launchCatching {
           farmManagerRepository.acceptUserRequest(user.userId)
        }
        uiState.value.farmRequests.remove(user)
        uiState.value.farmMembers.add(user)
    }

    fun rejectUser(user: FarmUserRelation) {
        launchCatching {
            farmManagerRepository.declineUserRequest(user.userId)
        }
        uiState.value.farmMembers.remove(user)
    }

    fun toggleAdmin(user: FarmUserRelation) {
        launchCatching {
            farmManagerRepository.toggleAdmin(user.userId)
        }
    }

    fun pageStartup() {
        launchCatching {
            val requests = farmManagerRepository.getJoinRequests()
            if(!requests.isNullOrEmpty()){
                requests.forEach{request ->
                    val userName = userManagerRepository.getUserName(request.userId)
                    uiState.value.userNameMap[request.userId] = userName
                }
                uiState.value = uiState.value.copy(farmRequests = requests.toMutableStateList())
            }

            val users = farmManagerRepository.getFarmUsers()
            if(!users.isNullOrEmpty()){
                users.forEach{user ->
                    val userName = userManagerRepository.getUserName(user.userId)
                    uiState.value.userNameMap[user.userId] = userName
                }
                uiState.value = uiState.value.copy(farmMembers = users.toMutableStateList())
            }


        }
    }

}