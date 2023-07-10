package com.example.tracktor.data.model

data class FarmUserRelation (
    val userId : String = "",
    val farmId : String = "",
    @field:JvmField
    val isAdmin : Boolean = false,
    @field:JvmField
    val isActive : Boolean = false,
)