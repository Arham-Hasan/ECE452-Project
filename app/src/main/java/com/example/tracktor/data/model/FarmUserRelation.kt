package com.example.tracktor.data.model

data class FarmUserRelation (
    val userId : String = "",
    val farmId : String = "",
    val isAdmin : Boolean = false,
    val isActive : Boolean = false,
)