package com.example.tracktor.data.repository

import android.net.Uri
import android.util.Log
import com.example.tracktor.data.model.Inventory
import com.example.tracktor.data.model.InventoryItem
import com.example.tracktor.data.model.UserInventoryStat
import com.example.tracktor.data.model.UserTransaction
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Transaction
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): InventoryRepository{

    override suspend fun createInventory(id: String) {
        val emptyHashMap = hashMapOf<String, Any>()
        firestore.collection("inventory").document(id)
            .set(emptyHashMap).await()
    }

    override suspend fun getInventoryById(id: String) : DocumentSnapshot {
        val result = firestore.collection("inventory").document(id).get().await()
        return result
    }

    override suspend fun itemExists(name: String, inventoryId: String): Boolean {
        val result = firestore.collection("inventory").document(inventoryId).get().await()
        val data = result.data
        if(data == null || !data.containsKey(name)){
            return false
        }
        return true
    }

    override suspend fun addItem(name: String, inventoryId: String, itemPrice: Double, imageRef : String?) {
        val item = InventoryItem(name = name, itemPrice = itemPrice, imageRef = imageRef)
        val newFieldData = hashMapOf(
            name to item
        )
        firestore.collection("inventory").document(inventoryId)
            .set(newFieldData, SetOptions.merge()).await()
    }

    override suspend fun userStatExistsForItem(
        itemName: String,
        userId: String,
        inventoryId: String
    ): Boolean {
        val result = firestore.collection("inventory").document(inventoryId).get().await()
        if(result == null || !result.contains(FieldPath.of(itemName,"userStats",userId))){
            return false
        }
        return true
    }

    override suspend fun addUserStatForItem(itemName: String, userId: String, inventoryId: String) {
        val map = mapOf(
            userId to UserInventoryStat()
        )

        val map2 = mapOf(
            "userStats" to map
        )

        val map3 = mapOf(
            itemName to map2
        )
        firestore.collection("inventory").document(inventoryId).set(map3, SetOptions.merge())
    }

    override suspend fun addPickTransaction(
        pickTransaction: UserTransaction,
        itemName: String,
        userId: String,
        inventoryId: String
    ) {
        val doc = firestore.collection("inventory").document(inventoryId).get().await()
        doc.reference.update(FieldPath.of(itemName,"userStats",userId,"pickList"), FieldValue.arrayUnion(pickTransaction)).await()
        doc.reference.update(FieldPath.of(itemName,"userStats",userId,"pickTotal"), FieldValue.increment(pickTransaction.amount.toLong())).await()
        doc.reference.update(FieldPath.of(itemName,"itemTotal"), FieldValue.increment(pickTransaction.amount.toLong())).await()
    }

    override suspend fun addSellTransaction(
        sellTransaction: UserTransaction,
        itemName: String,
        userId: String,
        inventoryId: String
    ) {
        val doc = firestore.collection("inventory").document(inventoryId).get().await()
        doc.reference.update(FieldPath.of(itemName,"userStats",userId,"sellList"), FieldValue.arrayUnion(sellTransaction))
        doc.reference.update(FieldPath.of(itemName,"userStats",userId,"sellTotal"), FieldValue.increment(sellTransaction.amount.toLong()))
        doc.reference.update(FieldPath.of(itemName,"itemTotal"), FieldValue.increment(-1*sellTransaction.amount.toLong())).await()
    }

    override suspend fun getItemNames(inventoryId: String): List<String>? {
        val documentSnapshot = firestore.collection("inventory").document(inventoryId).get().await()
        return documentSnapshot.data?.keys?.toList()
    }

    override suspend fun getItemNameToPrice(inventoryId: String): Map<String, Double> {
        val nameList = getItemNames(inventoryId)
        val documentSnapshot = firestore.collection("inventory").document(inventoryId).get().await()
        var map = mutableMapOf<String,Double>()
        if(!nameList.isNullOrEmpty()){
            for(name in nameList){
                map[name] = documentSnapshot.get(FieldPath.of(name,"itemPrice")) as Double
            }
        }
        return map
    }

}