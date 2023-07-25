package com.example.tracktor.data.repository

import com.example.tracktor.data.model.Farm
import com.example.tracktor.data.model.FarmUserRelation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FarmUserRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): FarmUserRepository {
    override suspend fun createFarm(farm: Farm, userId: String){
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farm.id, isAdmin = true, isActive = true)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()
    }

    override suspend fun getActiveFarmIds(userId: String): List<String?> {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId",userId)
            .whereEqualTo("isActive",true)
            .get().await()
        val farmIds : MutableList<String?> = mutableListOf<String?>()
        for(document in result){
            farmIds.add(document.getString("farmId"))
        }
        return farmIds
    }

    override suspend fun isAdmin(userId: String, farm: Farm): Boolean {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farm.id)
            .whereEqualTo("isAdmin", true)
            .get().await()
        return result.documents.isNotEmpty()
    }

    override suspend fun deleteFarm(farm: Farm) {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("farmId", farm.id)
            .get().await()
        for(document in result){
            document.reference.delete().await()
        }
    }

    override suspend fun isActiveFarmMember(userId: String, farmId: String): Boolean {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("isActive", true)
            .get().await()
        return !result.isEmpty()
    }

    override suspend fun isNonActiveFarmMember(userId: String, farmId: String): Boolean {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("isActive", false)
            .get().await()
        return !result.isEmpty()
    }

    override suspend fun isActiveOrNonActiveFarmMember(userId: String, farmId: String): Boolean {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farmId)
            .get().await()
        return !result.isEmpty()
    }

    override suspend fun addNonActiveUserToFarm(userId: String, farmId: String) {
        val farmUserRelation = FarmUserRelation(userId = userId, farmId = farmId, isAdmin = false, isActive = false)
        firestore.collection("farmUserRelation").add(farmUserRelation).await()
    }

    override suspend fun deleteFarmUserRelation(userId: String, farmId: String) {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("userId", userId)
            .get().await()
        if(!result.isEmpty)result.documents[0].reference.delete()
    }

    override suspend fun setUserToActive(userId: String, farmId: String) {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("userId", userId)
            .get().await()
        if(!result.isEmpty) result.documents[0].reference.update("isActive",true)
    }

    override suspend fun getNonActiveUsers(farmId: String): List<FarmUserRelation> {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("isActive", false)
            .get().await()
        val requests = mutableListOf<FarmUserRelation>()
        if(!result.isEmpty){
            for(doc in result.documents){
                requests.add(FarmUserRelation(userId = doc.getString("userId")!!, farmId = farmId, isAdmin = false,isActive = false))
            }
        }
        return requests
    }

    override suspend fun getActiveUsers(farmId: String): List<FarmUserRelation> {
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("farmId", farmId)
            .whereEqualTo("isActive", true)
            .get().await()
        val users = mutableListOf<FarmUserRelation>()
        if(!result.isEmpty){
            for(doc in result.documents){
                users.add(FarmUserRelation(userId = doc.getString("userId")!!, farmId = farmId, isAdmin = doc.getBoolean("isAdmin")!!,isActive = true))
            }
        }
        return users
    }

    override suspend fun toggleAdmin(userId: String, farm: Farm): Unit {
        val admin = isAdmin(userId, farm)
        val result = firestore.collection("farmUserRelation")
            .whereEqualTo("userId", userId)
            .whereEqualTo("farmId", farm.id)
            .get().await()
        if(!result.isEmpty) result.documents[0].reference.update("isAdmin",!admin)
    }

}