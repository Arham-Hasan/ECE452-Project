package com.example.tracktor.data.repository

import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.data.model.InstagramPost
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject


class InstagramPostRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore): InstagramPostRepository{
    override suspend fun getInstagramPosts(fridge: Fridge):List<Pair<String, List<InstagramPost>>>{
        val doc = firestore.collection("instagram").document(fridge.db_doc_id).get().await()
        val dataMapList = doc.get("posts") as? List<Map<String,String>>
        val posts = dataMapList!!.map { dataMap ->
            InstagramPost(
                image_url = dataMap["image_url"] as String,
                time_stamp = dataMap["time_stamp"] as String,
            )

        }
        return sortPosts(posts).toList().take(5)

    }

    private fun sortPosts(posts: List<InstagramPost>):Map<String, List<InstagramPost>>{

        val estTimeZone = TimeZone.getTimeZone("America/New_York")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        dateFormat.timeZone = estTimeZone

        // Group posts by day in the timestamp
        val groupedByDay = mutableMapOf<String, MutableList<InstagramPost>>()
        for (post in posts) {
            val date = dateFormat.parse(post.time_stamp)
            val day = SimpleDateFormat("MMMM dd yyyy", Locale.getDefault()).format(date)

            if (!groupedByDay.containsKey(day)) {
                groupedByDay[day] = mutableListOf()
            }
            groupedByDay[day]?.add(post)
        }

        // Sort groups by most recent day and sort posts within each group by timestamp
        return groupedByDay
            .toSortedMap(reverseOrder())
            .mapValues { (_, posts) -> posts.sortedByDescending { dateFormat.parse(it.time_stamp) } }

    }


}