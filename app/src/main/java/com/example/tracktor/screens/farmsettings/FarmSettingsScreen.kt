package com.example.tracktor.screens.farmsettings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tracktor.common.composable.AlertDialogConfirm
import com.example.tracktor.common.composable.AlertTextConfirm
import com.example.tracktor.common.composable.BasicToolbar
import com.example.tracktor.common.composable.SettingOption
import com.example.tracktor.ui.theme.TracktorTheme

@Composable
fun FarmSettingsScreen(
    openScreen: (String) -> Unit,
    clearAndNavigate: (String) -> Unit,
    viewModel: FarmSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    FarmSettingsScreenContent(
        uiState.isAdmin,
        uiState.deleteFarmAlert,
        uiState.changeFarmNameAlert,
        { viewModel.onManageMembersClick(openScreen) },
        { viewModel.onChangeFarmNameClick() },
        { viewModel.onLeaveFarmClick() },
        { viewModel.onDeleteFarmClick() },
        { viewModel.toggleDeleteFarmAlert() },
        { viewModel.toggleChangeFarmNameAlert() },
        viewModel::onNewFarmNameChange,
        uiState.newFarmName,
        { viewModel.comfirmRenameFarm(clearAndNavigate) },
        { viewModel.comfirmDeleteFarm(clearAndNavigate) }
    )
}


@Composable
fun FarmSettingsScreenContent(
    isAdmin: Boolean,
    deleteFarmVisible: Boolean,
    changeNameVisible: Boolean,
    onManageMemberClick: () -> Unit,
    onRenameFarmClick: () -> Unit,
    onLeaveFarmClick: () -> Unit,
    onDeleteFarmClick: () -> Unit,
    toggleDeleteFarm: () -> Unit,
    toggleChangeNameFarm: () -> Unit,
    onNameChange: (String) -> Unit,
    newName: String,
    onNameConfirm: () -> Unit,
    onDeleteConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier

    ) {
        BasicToolbar("Farm Settings")
        Column(modifier = Modifier
            .padding(vertical = 8.dp)
            .padding(16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4),
            ) {
                Column {
                    AlertDialogConfirm(
                        onDeleteConfirm,
                        {},
                        toggleDeleteFarm,
                        AlertVisible = deleteFarmVisible,
                        mainText = "Are you sure you want to delete the farm?",
                        secondText = "This will permanently delete the farm for all users."
                    )
                    AlertTextConfirm(
                        onNameConfirm,
                        {},
                        toggleChangeNameFarm,
                        AlertVisible = changeNameVisible,
                        mainText = "Enter the new name for this farm",
                        textFieldPlaceholder = "New Farm Name",
                        value = newName,
                        onNewValue = onNameChange,
                        label = "Enter New Name"

                    )
                    if (isAdmin) {
                        SettingOption(
                            onManageMemberClick,
                            "Manage Members",
                            "Approve/ Decline join requests, Change admins, Remove members"
                        )
                        SettingOption(
                            onRenameFarmClick,
                            "Rename Farm",
                            "Change the name of the farm",
                        )
                    }

                    SettingOption(
                        onLeaveFarmClick,
                        "Leave Farm",
                        "",
                    )

                    if (isAdmin) {
                        SettingOption(
                            onDeleteFarmClick,
                            "Delete Farm",
                            "Permanently delete the farm for all users",
                            textColor = Color.Red
                        )
                    }
                }

            }
        }

    }

}

@Preview
@Composable
fun FarmSettingsScreenContentPreview() {
    TracktorTheme {
        FarmSettingsScreenContent(
            true, false, false, {}, {}, {}, {}, {}, {}, {}, "", {}, {}
        )
    }
}

