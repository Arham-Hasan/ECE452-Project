package com.example.tracktor.screens.managemembers

import com.example.tracktor.data.model.FarmUserRelation

data class ManageMembersUiState (
    val dropDrownExtended: Boolean = false,
    val farmMembers: List<FarmUserRelation> = listOf<FarmUserRelation>(),
    val farmRequests: List<FarmUserRelation> = listOf<FarmUserRelation>()
)