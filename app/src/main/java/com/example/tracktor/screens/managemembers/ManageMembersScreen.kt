package com.example.tracktor.screens.managemembers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tracktor.common.composable.AcceptButton
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.RejectButton
import com.example.tracktor.data.model.FarmUserRelation

@Composable
fun ManageMembersScreen(openAndPopUp: (String, String) -> Unit, viewModel: ManageMembersViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    LaunchedEffect(viewModel) {
        viewModel.pageStartup()
    }

    PickingModeScreenContent(
        uiState,
        viewModel::acceptUser,
        viewModel::rejectUser,
        viewModel::toggleAdmin,
        viewModel.currUserId,
        viewModel.currFarmId
    )


}

@Composable
fun PickingModeScreenContent(
    uiState: ManageMembersUiState,
    acceptUser: (FarmUserRelation) -> Unit,
    rejectUser: (FarmUserRelation) -> Unit,
    toggleAdmin: (FarmUserRelation) -> Unit,
    currentUserId: String,
    currentFarmId: String
)
{

    Column(
        Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicToolbar("Farm Settings")
        Column(modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(16.dp)) {
            Column(Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                androidx.compose.material3.Text(text = "Invite users to the farm by sharing the farm ID!")
                androidx.compose.material3.Text(text = "$currentFarmId")

                Spacer(modifier = Modifier.height(32.dp)) // Add spacing between the rows
                if(uiState.farmRequests.isNotEmpty()) {
                    androidx.compose.material3.Text(text = "Requests to join farm")
                    uiState.farmRequests.forEach { user ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            {}
                            androidx.compose.material3.Text(text = uiState.userNameMap[user.userId]!!)
                            AcceptButton(action = { acceptUser(user) })
                            RejectButton(action = { rejectUser(user) })
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp)) // Add spacing between the rows
                }
                if(uiState.farmMembers.isNotEmpty()) {
                    androidx.compose.material3.Text(text = "Farm Members")
                    uiState.farmMembers.forEach { user ->
                        var checked = remember { mutableStateOf(user.isAdmin) }
                        Row(
                            modifier  = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material3.Text(text = uiState.userNameMap[user.userId]!!) // this should use getUserName
                            if(currentUserId != user.userId) {
                                Checkbox(
                                    checked = checked.value,
                                    onCheckedChange = {
                                        toggleAdmin(user)
                                        checked.value = it
                                    }
                                )
                                RejectButton(action = { rejectUser(user)} )
                            }

                        }
                    }
                } else {
                    androidx.compose.material3.Text(text = "You have no users on the farm")
                }
            }
        }
    }
}

