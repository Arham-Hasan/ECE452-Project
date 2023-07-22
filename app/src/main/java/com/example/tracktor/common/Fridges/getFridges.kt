package com.example.tracktor.common.Fridges

import com.google.android.gms.maps.model.LatLng

fun getFridges():List<Fridge>{
    return listOf(KitchenerFridge, FullCircleFoods)
}

val KitchenerFridge= Fridge(
    name = "Community Fridge KW",
    address = "300 King St E, Kitchener, ON N2H 2V5",
    hashtag = "#kitchenermarket",
    db_doc_id = "kitchenermarket",
    latlng = LatLng(43.4480255, -80.4862383)
)

val FullCircleFoods= Fridge(
    name = "Full Circle Foods Fridge",
    address = "3 Charles St W, Kitchener, ON N2G 1H1",
    hashtag = "#fullcirclefoods",
    db_doc_id = "fullcirclefoods",
    latlng = LatLng(43.4490026, -80.4904216)
)
val fridgeMapping = mapOf<String,Fridge>(
    Pair("Community Fridge KW",KitchenerFridge),
    Pair("Full Circle Foods Fridge",FullCircleFoods)
)