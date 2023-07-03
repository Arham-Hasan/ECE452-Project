package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FarmRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): FarmRepository {
    override suspend fun getFarms(userId: String) : List<Farm?> {
        val result = firestore.collection("farmUserRelation").whereEqualTo("userId",userId).get().await()
        val farmIds : MutableList<String?> = mutableListOf<String?>()
        for(document in result){
            farmIds.add(document.getString("farmId"))
        }
        val farms : MutableList<Farm?> = mutableListOf<Farm?>()
        if (farmIds.isEmpty()) return farms
        val result2 = firestore.collection("farms").whereIn("id",farmIds).get().await()
        for(document in result2){
            farms.add(Farm(id = document.getString("id")!!, name = document.getString("name")!!))
        }
        return farms
    }
    override suspend fun createFarm(name: String, userId: String) : Unit{
        val farm = Farm(name = name, id = UUID.randomUUID().toString() )
        firestore.collection("farms").add(farm).await()
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farm.id)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()
    }
}