package com.example.tracktor.screens.managemembers

import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import com.example.tracktor.common.snackbar.SnackbarManager
import com.example.tracktor.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.example.tracktor.data.model.User
import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.screens.TracktorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ManageMembersViewModel @Inject constructor(
    userManagerRepository: UserManagerRepository
) : TracktorViewModel(userManagerRepository)  {

    var uiState = mutableStateOf(ManageMembersUiState())
        private set

    fun acceptUser(user: FarmUserRelation) {
        println("user accepted")
//        This function sets the accepted boolean on the farm thing to true
    }

    fun rejectUser(user: FarmUserRelation) {
        println("user rejected")
//        this function should delete the entry of the
//        farm request no matter if the user is part of the farm or not
    }

    fun getUserName(user: FarmUserRelation): String {

        println("return user name")
//        This function should return the users name so we can display the users
//        name instead of their userID
        return user.userId
    }

    fun toggleAdmin(user: FarmUserRelation) {
        println("user admin toggled")
//        this function should toggle the admin permission of the user, if they
//        are admin then the checkbox is filled
    }



}