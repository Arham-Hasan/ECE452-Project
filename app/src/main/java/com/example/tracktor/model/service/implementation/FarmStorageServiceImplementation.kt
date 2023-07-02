package com.example.tracktor.model.service.implementation

import android.util.Log
import com.example.tracktor.model.Farm
import com.example.tracktor.model.FarmUserRelation
import com.example.tracktor.model.service.FarmStorageService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FarmStorageServiceImplementation
@Inject
constructor(private val firestore: FirebaseFirestore) :
    FarmStorageService{
    override suspend fun getFarmsFromUserId(userId: String): List<Farm?> {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId",userId).get().await()
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

    override suspend fun createFarm(name: String, userId: String) {
        val farm = Farm(name = name, id = UUID.randomUUID().toString() )
        firestore.collection("farms").add(farm).await()
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farm.id, isAdmin = true)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()
    }

    override suspend fun deleteFarm(farmId: String, userId: String) {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("isAdmin", true)
            .get().await()
        if (result.documents.isNotEmpty()) {
            val farmDoc = result.documents[0]
            val result2 = firestore.collection("farms")
                .whereEqualTo("farmId", farmDoc.getString("farmId"))
                .get().await()
            if (result2.documents.isNotEmpty()) {
                result2.documents[0].reference.delete().await()
            }
        }
    }
    override suspend fun requestToJoinFarm(farmId: String, userId: String) {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .get().await()
        if(result.documents.isEmpty()){
            val result2 = firestore.collection("farmUserRequest")
                .whereEqualTo("userId", userId)
                .whereEqualTo("farmId", farmId)
                .get().await()
            if(result2.documents.isEmpty()){
                val farmUserRelation = FarmUserRelation(userId = userId, farmId = farmId, isAdmin = false)
                firestore.collection("farmUserRequest").add(farmUserRelation).await()
            }
        }
    }

    override suspend fun deleteRequestToJoinFarm(farmId: String, userId: String) {
        val result = firestore.collection("farmUserRequest")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .get().await()
        if(result.documents.isNotEmpty()){
            result.documents[0].reference.delete().await()
        }
    }

    override suspend fun acceptRequestToJoinFarm(farmId: String, userId: String) {
        deleteRequestToJoinFarm(farmId, userId)
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farmId, isAdmin = false)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()

    }

    }
