package com.example.tracktor.model.service

import com.example.tracktor.model.Farm

interface FarmStorageService  {
    suspend fun getFarmsFromUserId(userId: String) : List<Farm?>
}