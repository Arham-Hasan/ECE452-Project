package com.example.tracktor.data.repository

import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.data.model.InstagramPost

interface InstagramPostRepository {
    suspend fun getInstagramPosts(fridge: Fridge): List<Pair<String, List<InstagramPost>>>
}