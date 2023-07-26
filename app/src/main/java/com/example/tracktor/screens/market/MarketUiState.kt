package com.example.tracktor.screens.market

import com.example.tracktor.common.Fridges.Fridge
import com.example.tracktor.data.model.InstagramPost

data class MarketUiState (
    val posts:List<Pair<String, List<InstagramPost>>> = listOf(),
    val fridge:Fridge = Fridge(),
    val hashtagAlert: Boolean= false,
)