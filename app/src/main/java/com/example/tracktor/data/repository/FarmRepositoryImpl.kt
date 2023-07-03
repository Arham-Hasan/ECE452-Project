package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FarmRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): FarmRepository {
    override suspend fun getFarms(farmIds: List<String?>) : List<Farm?> {

        val farms : MutableList<Farm?> = mutableListOf<Farm?>()
        if (farmIds.isEmpty()) return farms
        val result2 = firestore.collection("farms").whereIn("id",farmIds).get().await()
        for(document in result2){
            farms.add(Farm(id = document.getString("id")!!, name = document.getString("name")!!))
        }
        return farms
    }
    override suspend fun createFarm(farm:Farm) : Unit{
        firestore.collection("farms").add(farm).await()
    }
}