package com.example.tracktor.screens.managemembers

import com.example.tracktor.data.model.FarmUserRelation

data class ManageMembersUiState(
    val dropDrownExtended: Boolean = false,
    val farmMembers: MutableList<FarmUserRelation> = mutableListOf<FarmUserRelation>(),
    val farmRequests: MutableList<FarmUserRelation> = mutableListOf<FarmUserRelation>(),
    val userNameMap: MutableMap<String, String> = mutableMapOf<String, String>()

)