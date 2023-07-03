package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FarmUserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): FarmUserRepository {
    override suspend fun createFarm(farm: Farm, userId: String){
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farm.id)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()
    }

    override suspend fun getFarmIds(userId: String): List<String?> {
        val result = firestore.collection("farmUserRelation").whereEqualTo("userId",userId).get().await()
        val farmIds : MutableList<String?> = mutableListOf<String?>()
        for(document in result){
            farmIds.add(document.getString("farmId"))
        }
        return farmIds
    }
}