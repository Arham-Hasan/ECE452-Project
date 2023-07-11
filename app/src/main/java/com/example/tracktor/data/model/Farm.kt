package com.example.tracktor.data.model

data class Farm(
    val id:String = "",
    val name:String = "",
    val inventory:HashMap<String,String> = HashMap(),
)