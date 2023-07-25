package com.example.tracktor.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageStorageManagerImpl @Inject constructor(
    private val storage: FirebaseStorage,
): ImageStorageManager {
    override suspend fun uploadImage(imageRef: String?, imageUri: Uri) {
        val storageRef = storage.reference.child(imageRef!!)
        storageRef.putFile(imageUri).await()
    }

    override suspend fun getImage(imageRef: String?): Any? {
        val storageRef = storage.reference.child(imageRef!!)
        val ONE_MEGABYTE: Long = 1024 * 1024
        return storageRef.getBytes(ONE_MEGABYTE).await()
    }
}