package com.example.tracktor.common.Fridges

import com.google.android.gms.maps.model.LatLng

data class Fridge (
    val name:String = "",
    val address: String = "",
    val hashtag: String = "",
    val db_doc_id: String = "",
    val latlng: LatLng = LatLng(0.0,0.0)
)