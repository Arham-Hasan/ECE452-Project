package com.example.tracktor.data.repository

import android.util.Log
import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FarmRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): FarmRepository {
    override suspend fun getFarms(farmIds: List<String?>) : List<Farm?> {

        val farms : MutableList<Farm?> = mutableListOf<Farm?>()
        if (farmIds.isEmpty()) return farms
        val result2 = firestore.collection("farms").whereIn("id",farmIds).get().await()
        for(document in result2){
            farms.add(Farm(id = document.getString("id")!!, name = document.getString("name")!!,
            inventoryId = document.getString("inventoryId")!!))
        }
        return farms
    }
    override suspend fun createFarm(farm:Farm) : Unit{
        firestore.collection("farms").add(farm).await()
    }

    override suspend fun deleteFarm(farm: Farm) {
        val result2 = firestore.collection("farms")
            .whereEqualTo("id", farm.id)
            .get().await()
        if (result2.documents.isNotEmpty()) {
            result2.documents[0].reference.delete().await()
        }
    }

    override suspend fun farmExists(farmId: String): Boolean {
        val result = firestore.collection("farms")
            .whereEqualTo("id", farmId)
            .get().await()
        return !result.isEmpty()
    }
}