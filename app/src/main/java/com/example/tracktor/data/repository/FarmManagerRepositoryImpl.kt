package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import javax.inject.Inject

class FarmManagerRepositoryImpl @Inject constructor(private val authRepository: AuthRepository, private val farmRepository: FarmRepository): FarmManagerRepository {
    override suspend fun getFarms() : List<Farm?>{
        return farmRepository.getFarms(authRepository.currentUserId)
    }
    override suspend fun createFarm(name: String) : Unit{
        farmRepository.createFarm(name,authRepository.currentUserId)
    }
}