package com.example.tracktor.data.repository

import android.net.Uri

interface ImageStorageRepository {
    suspend fun uploadImage(imageRef: String?, imageUri: Uri) : Unit
    suspend fun getImage(imageRef: String) : ByteArray
}