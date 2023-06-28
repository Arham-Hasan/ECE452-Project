package com.example.tracktor.model.service.implementation

import android.util.Log
import com.example.tracktor.model.Farm
import com.example.tracktor.model.service.FarmStorageService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FarmStorageServiceImplementation
@Inject
constructor(private val firestore: FirebaseFirestore) :
    FarmStorageService{
    override suspend fun getFarmsFromUserId(userId: String): List<Farm?> {
        val result = firestore.collection("farmUserRelation").whereEqualTo("userId",userId).get().await()
        val farmIds : MutableList<String?> = mutableListOf<String?>()
        for(document in result){
            farmIds.add(document.getString("farmId"))
        }
        val result2 = firestore.collection("farms").whereIn("id",farmIds).get().await()
        val farms : MutableList<Farm?> = mutableListOf<Farm?>()
        for(document in result2){
            farms.add(Farm(id = document.getString("id")!!, name = document.getString("name")!!))
        }
        return farms
    }
    }